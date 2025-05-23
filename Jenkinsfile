pipeline {
    agent any

    environment {
        AZURE_RESOURCE_GROUP = 'SWII-CICD'
        AZURE_APP_NAME = 'productosjson'
        AZURE_REGION = 'Canada Central'
        MAVEN_HOME = tool 'Maven'
        JAVA_HOME = tool 'JDK-17'
    }

    tools {
        maven 'Maven'
        jdk 'JDK-17'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Json-Esutpinan/Examen2SWII.git'
            }
        }

        stage('Build Application') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
            }
        }

        stage('Deploy to Azure App Service') {
            steps {
                // withCredentials inyecta las variables de entorno:
                // AZURE_CLIENT_ID, AZURE_CLIENT_SECRET, AZURE_TENANT_ID, AZURE_SUBSCRIPTION_ID
                withCredentials([azureServicePrincipal('azure-service-principal')]) { // Asegúrate que 'azure-service-principal' es tu ID correcto
                    sh """
                        ${MAVEN_HOME}/bin/mvn azure-webapp:deploy \
                            -DresourceGroup=${AZURE_RESOURCE_GROUP} \
                            -DappName=${AZURE_APP_NAME} \
                            -Dregion="${AZURE_REGION}"
                    """
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."
        }
        failure {
            echo "Pipeline failed. Check logs for details."
        }
        success {
            echo "Pipeline succeeded. Application deployed to Azure App Service."
        }
    }
}
