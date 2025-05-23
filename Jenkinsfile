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
                // Puedes añadir -X aquí también si quieres más depuración en el build
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
            }
        }

        stage('Deploy to Azure App Service') {
            steps {
                withCredentials([azureServicePrincipal('azure-service-principal')]) {
                    sh """
                        # Ejecutar Maven en modo depuración (-X) para obtener logs detallados
                        ${MAVEN_HOME}/bin/mvn azure-webapp:deploy -X \
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
