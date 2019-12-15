pipeline {
  agent any
  environment {
    DOCKERHUBNAME = "superzyc"
  }
  stages {
    stage('Build') {
      steps {
        echo 'build starting...'
        // bat 'mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V'
        bat 'mvn -B -DskipTests clean package'
        echo 'maven clean and package successfully!'
      }
    }
    stage('Build Docker Image') {
      steps {
        echo "Starting building..."
        withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          echo '%USERNAME%'
          echo '%PASSWORD%'
          bat 'docker login -u %USERNAME% -p %PASSWORD%'
          echo 'Start building image...'
          bat 'docker image build -t %DOCKERHUBNAME%/sbasearch .'
          echo 'Image build successfully!'
          echo 'Start pushing image to docker hub...'
          bat 'docker push %DOCKERHUBNAME%/sbasearch'
          echo 'Image push successfully!'
          echo 'Start running...'
          bat 'docker run -d -p 8761:8761 --name sba-search %DOCKERHUBNAME%/sbasearch'
          echo 'docker running successfully!'
        }   
      }
    }
  }
  post {
    always {
      echo 'build and deploy finished'
    }

    failure {
      echo 'build failed'
    }

    success {
      echo 'deploy successfully'
    }
  }
}