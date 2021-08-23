
pipeline {
  agent {
    label 'maven'
  }
  parameters {
    choice choices: ['snapshot', 'release'], description: 'deploy model', name: 'DeployModel'
  } 
  environment {
    deployPort = '8080'
    exposePort = '5000'
    group = 'com.tapddemo'
    artifactId = "${currentBuild.projectName}"
    version = "${BUILD_NUMBER}"
    nexusPushUrl = '193.112.147.158:7720'
    nexusPullUrl = '193.112.147.158:7721'
    cdMachineHost = '1.14.181.160'
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
        script{
          if (params.DeployModel == 'release') {
            sh "mvn package -Dversion=${version} -DgroupId=${group} -DartifactId=${artifactId}"
            nexusPublisher nexusInstanceId: 'DevOpsNexus', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "target/${artifactId}-${version}.jar"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${group}", packaging: 'jar', version: "${version}"]]]        
          }else{
            sh "mvn package -Dversion=snapshot -DgroupId=${group} -DartifactId=${artifactId}"
            nexusPublisher nexusInstanceId: 'DevOpsNexus', nexusRepositoryId: 'maven-snapshot', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "target/${artifactId}-snapshot.jar"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${group}", packaging: 'jar', version: "snapshot"]]]        
          }
        }
        echo '----------Package Finished----------'
      }
    } 
    stage ('Ship') {
      steps {
        echo '----------Run Ship----------'
        script{
          docker.withRegistry("http://${nexusPushUrl}", 'DevOpsNexusPassword') {
            if (params.DeployModel == 'release') {
              def customImage = docker.build("${nexusPushUrl}/${imageOrg}-${artifactId}:${version}", "--build-arg jarname=${artifactId}-${version}.jar .")
              customImage.push()
            }else{
              def customImage = docker.build("${nexusPushUrl}/${imageOrg}-${artifactId}:snapshot", "--build-arg jarname=${artifactId}-snapshot.jar .")
              customImage.push()
            }
          }
        }
        echo '----------Ship Finished----------'
      }
    }
    stage ('Deploy') {
      steps {
        echo '----------Run Deploy----------'
        script{
          withCredentials([usernamePassword(credentialsId: 'DevOpsNexusPassword', passwordVariable: 'NEXUS_PASSWD', usernameVariable: 'NEXUS_USER')]) {
            sshagent (credentials: ['CD-Machine-SSH-Credential']) {
              sh "ssh -o StrictHostKeyChecking=no -p 36000 -l jenkins ${cdMachineHost} docker login -u $NEXUS_USER -p $NEXUS_PASSWD ${nexusPullUrl}"
              if (params.DeployModel == 'release') {
                sh "ssh -o StrictHostKeyChecking=no -p 36000 -l jenkins ${cdMachineHost} docker rm -f ${artifactId}-release"
                sh "ssh -o StrictHostKeyChecking=no -p 36000 -l jenkins ${cdMachineHost} docker run -d --pull always --name ${artifactId}-release -p ${deployPort}:${exposePort} ${nexusPullUrl}/${imageOrg}-${artifactId}:${version}"
              }else{
                sh "ssh -o StrictHostKeyChecking=no -p 36000 -l jenkins ${cdMachineHost} docker rm -f ${artifactId}-snapshot"
                sh "ssh -o StrictHostKeyChecking=no -p 36000 -l jenkins ${cdMachineHost} docker run -d --pull always --name ${artifactId}-snapshot  -p ${deployPort}:${exposePort} ${nexusPullUrl}/${imageOrg}-${artifactId}:snapshot"
              }              
            }          
          }
        }
        echo '----------Deploy Finished----------'
      }
    }         
  }
}