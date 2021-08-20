
pipeline {
  agent {
    label 'maven'
  }  
  environment {
    DEPLOY_PORT = '8080'
    group = 'com.tapddemo'
    artifactId = 'demo'
  }  
  stages {
    stage ('Compile') {
      steps {
        echo '----------Run Compile----------'
        sh 'mvn clean compile -Dversion=${BUILD_NUMBER} -DgroupId=${group} -DartifactId=${artifactId}'
        echo '----------Compile Finished----------'
      }
    }
    stage ('Unit Test') {
      steps {
          echo '----------Run Unit Test----------'
          sh 'mvn test -Dversion=${BUILD_NUMBER} -DgroupId=${group} -DartifactId=${artifactId}'
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
          sh 'mvn sonar:sonar -Dversion=${BUILD_NUMBER} -DgroupId=${group} -DartifactId=${artifactId}'
        }
        echo '----------SonarQube Scan Finished----------'
      }
    }    
    stage ('Package & Build') {
      steps {
        echo '----------Run Package----------'
        sh 'mvn package -Dversion=${BUILD_NUMBER} -DgroupId=${group} -DartifactId=${artifactId}'
        echo '----------Package Finished----------'
        echo '----------Run Build----------'

        // docker.withRegistry('https://registry.example.com', 'credentials-id') {

        //     def customImage = docker.build("my-image:${env.BUILD_ID}")

        //     /* Push the container to the custom Registry */
        //     customImage.push()
        // }        
        echo '----------Build Finished----------'
      }
    } 
    stage ('Ship') {
      steps {
        echo '----------Run Ship----------'
        nexusPublisher nexusInstanceId: 'DevOpsNexus', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'target/${currentBuild.projectName}-${BUILD_NUMBER}.jar']], mavenCoordinate: [artifactId: '${currentBuild.projectName}', groupId: '${group}', packaging: 'jar', version: '${BUILD_NUMBER}']]]        
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