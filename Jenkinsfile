
pipeline {
  agent {
    label 'maven'
  }  
  environment {
    DEPLOY_PORT = '8080'
  }  
  stages {
    stage ('Build') {
      steps {
        echo 'Run Build'
        sh 'mvn clean compile'
      }
    }
    stage ('Unit Test') {
      steps {
        echo 'Run Unit Test'
        sh 'mvn test'
      }
    }
    stage ('SonarQube Scan') {
      steps {
        echo 'Run SonarQube Scan'
        withSonarQubeEnv(installationName: 'DevOpsSonarQube') {
          sh 'mvn sonar:sonar'
        }
      }
    }    
    stage ('Package') {
      steps {
        echo 'Run Package'
        sh 'mvn package'
      }
    } 
    stage ('Ship') {
      steps {
        echo 'Run Ship'

      }
    }
    stage ('Deploy') {
      steps {
        echo 'Run Deploy'

      }
    }         
  }
}