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
                withCredentials([azureServicePrincipal('azure-service-principal')]) {
                    // *** PASO DE DEPURACIÓN CRÍTICO: Imprimir las variables de entorno ***
                    sh 'echo "Debugging Azure Service Principal Variables:"'
                    sh 'echo "AZURE_CLIENT_ID: ${AZURE_CLIENT_ID}"'
                    sh 'echo "AZURE_CLIENT_SECRET: ${AZURE_CLIENT_SECRET+xxxxxx}"'
                    sh 'echo "AZURE_TENANT_ID: ${AZURE_TENANT_ID}"'
                    sh 'echo "AZURE_SUBSCRIPTION_ID: ${AZURE_SUBSCRIPTION_ID}"'
                    sh 'echo "--- End Debugging ---"'

                    // El comando de despliegue.
                    // El plugin de Maven de Azure debería usar las variables de entorno inyectadas por withCredentials.
                    sh "${MAVEN_HOME}/bin/mvn azure-webapp:deploy " +
                       "-DresourceGroup=${AZURE_RESOURCE_GROUP} " +
                       "-DappName=${AZURE_APP_NAME} " +
                       "-Dregion='${AZURE_REGION}'"
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
