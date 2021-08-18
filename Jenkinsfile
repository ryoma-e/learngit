
pipeline {
  agent {
    label 'maven'
  }  
  environment {
    DEPLOY_PORT = '8080'
  }  
  stages {
    stage ('Compile') {
      steps {
        echo '----------Run Compile----------'
        sh 'mvn clean compile'
        echo '----------Compile Finished----------'
      }
    }
    stage ('Unit Test') {
      steps {
        echo '----------Run Unit Test----------'
        sh 'mvn test'
        echo '----------Unit Test Finished----------'
      }
    }
    stage ('SonarQube Scan') {
      steps {
        echo '----------Run SonarQube Scan----------'
        withSonarQubeEnv(installationName: 'DevOpsSonarQube') {
          sh 'mvn sonar:sonar'
        }
        echo '----------SonarQube Scan Finished----------'
      }
    }    
    stage ('Package & Build') {
      steps {
        echo '----------Run Package----------'
        sh 'mvn package'
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
        sh 'mvn deploy'
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