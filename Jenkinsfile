
pipeline {
  agent {
    label 'maven'
  }
  parameters {
    choice choices: ['snapshot', 'release'], description: 'deploy model', name: 'DeployModel'
  } 
  environment {
    deployPort = '8080'
    group = 'com.tapddemo'
    artifactId = "${currentBuild.projectName}"
    version = "${BUILD_NUMBER}"
    nexusUrl = '193.112.147.158:7720'
    imageOrg = 'tapdcdapp'
  }  
  stages {
    stage ('Compile') {
      steps {
        echo '----------Run Compile----------'
        sh "mvn clean compile -Dversion=${version} -DgroupId=${group} -DartifactId=${artifactId}"
        echo '----------Compile Finished----------'
      }
    }
    stage ('Unit Test') {
      steps {
          echo '----------Run Unit Test----------'
          sh "mvn test -Dversion=${version} -DgroupId=${group} -DartifactId=${artifactId}"
          echo '----------Unit Test Finished----------'
      }
      post {
          always {
              tapdTestReport frameType: 'JUnit', onlyNewModified: true, reportPath: 'target/surefire-reports/*.xml'
          }
      }      
    }
    stage ('SonarQube Scan') {
      steps {
        echo '----------Run SonarQube Scan----------'
        withSonarQubeEnv(installationName: 'DevOpsSonarQube') {
          sh "mvn sonar:sonar -Dversion=${version} -DgroupId=${group} -DartifactId=${artifactId}"
        }
        echo '----------SonarQube Scan Finished----------'
      }
    }    
    stage ('Package') {
      steps {
        echo '----------Run Package----------'
        sh "mvn package -Dversion=${version} -DgroupId=${group} -DartifactId=${artifactId}"
        script{
          if (params.DeployModel == 'release') {
            nexusPublisher nexusInstanceId: 'DevOpsNexus', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "target/${artifactId}-${version}.jar"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${group}", packaging: 'jar', version: "${version}"]]]        
          }else{
            nexusPublisher nexusInstanceId: 'DevOpsNexus', nexusRepositoryId: 'maven-snapshots', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "target/${artifactId}-${version}.jar"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${group}", packaging: 'jar', version: "snapshots"]]]        
          }
        }
        echo '----------Package Finished----------'
      }
    } 
    stage ('Ship') {
      steps {
        echo '----------Run Ship----------'
        script{
          docker.withRegistry("http://${nexusUrl}", 'DevOpsNexusPassword') {
            if (params.DeployModel == 'release') {
              def customImage = docker.build("${nexusUrl}/${imageOrg}-${artifactId}:${version}", "--build-arg jarname=${artifactId}-${version}.jar .")
            }else{
              def customImage = docker.build("${nexusUrl}/${imageOrg}-${artifactId}:snapshot", "--build-arg jarname=${artifactId}-${version}.jar .")
            }
            customImage.push()
          }
        }
        echo '----------Ship Finished----------'
      }
    }
    stage ('Deploy') {
      steps {
        echo '----------Run Deploy----------'
        echo '----------Deploy Finished----------'
      }
    }         
  }
}