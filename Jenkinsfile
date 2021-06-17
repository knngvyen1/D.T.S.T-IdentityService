pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -DargLine="-Dspring.profiles.active=test" test'
            }
        }

        stage('Release') {
            steps {
                bat "docker build -t eu.gcr.io/dtst-tool/user-management ."
                bat "docker push eu.gcr.io/dtst-tool/user-management"
            }
        }

        stage('Deploy') {
            steps {
                bat "kubectl apply -f ./k8s-manifests"
                bat "kubectl rollout restart deployment/user-management"
            }
        }
    }
}