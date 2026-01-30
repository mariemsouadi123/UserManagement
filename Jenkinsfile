pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "mariemsouadi12189"
        BACKEND_IMAGE  = "usermanagement"  // Docker image name
        IMAGE_TAG      = "latest"          // fixed tag to reuse
    }

    stages {

        stage("Checkout Code") {
            steps {
                git branch: 'master',
                    url: 'https://github.com/mariemsouadi123/UserManagement.git'
            }
        }

        stage("Build Docker Image") {
            steps {
                script {
                    // Build Docker image with cache
                    docker.build(
                        "${DOCKERHUB_USER}/${BACKEND_IMAGE}:${IMAGE_TAG}",
                        "."
                    )
                }
            }
        }

        stage("Push Docker Image") {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh """
                        echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin

                        docker push ${DOCKERHUB_USER}/${BACKEND_IMAGE}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage("Deploy to Kubernetes") {
            steps {
                withCredentials([
                    file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE')
                ]) {
                    sh """
                        export KUBECONFIG=\$KUBECONFIG_FILE

                        kubectl apply -f k8s/usermanagement-deployment.yaml
                        kubectl apply -f k8s/usermanagement-service.yaml

                        kubectl rollout status deployment/usermanagement-deployment -n default
                    """
                }
            }
        }
    }

    post {
        success { echo "✅ Pipeline successful!" }
        failure { echo "❌ Pipeline failed!" }
    }
}
