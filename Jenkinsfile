pipeline {
    agent any // Indica que el pipeline puede ejecutarse en cualquier agente Jenkins disponible

    // Define variables de entorno para usar en el pipeline
    environment {
        // Asegúrate de que estos valores coincidan con tus recursos de Azure
        AZURE_RESOURCE_GROUP = 'SWII-CICD' // Reemplaza con tu grupo de recursos
        AZURE_APP_NAME = 'productosjson' // Reemplaza con el nombre de tu App Service
        AZURE_REGION = 'Canada Central' // Reemplaza con la región de tu App Service (ej. 'eastus', 'southcentralus')
        MAVEN_HOME = tool 'Maven' // Nombre de la instalación de Maven configurada en Jenkins Global Tool Configuration
        JAVA_HOME = tool 'JDK-17' // Nombre de la instalación de JDK 17 configurada
    }

    // Define las herramientas que usará el pipeline
    tools {
        maven 'Maven'
        jdk 'JDK-17'
    }

    stages {
        stage('Checkout Code') { // Etapa para clonar el código fuente
            steps {
                git branch: 'main', url: 'https://github.com/Json-Esutpinan/Examen2SWII.git'
            }
        }

        stage('Build Application') { // Etapa para construir la aplicación con Maven
            steps {
                sh "mvn clean install -DskipTests" // Construye el JAR, saltando los tests
            }
        }

        stage('Deploy to Azure App Service') { // Etapa para desplegar a Azure
            steps {
                withCredentials([azureServicePrincipal('azure-service-principal')]) {
                sh "${MAVEN_HOME}/bin/mvn azure-webapp:deploy " +
                   "-DresourceGroup=${AZURE_RESOURCE_GROUP} " +
                   "-DappName=${AZURE_APP_NAME} " +
                   "-Dregion='${AZURE_REGION}' " +
                   "-Dazure.auth.type=service_principal " +
                   "-Dazure.clientid=${AZURE_CLIENT_ID} " +
                   "-Dazure.clientsecret=${AZURE_CLIENT_SECRET} " +
                   "-Dazure.tenantid=${AZURE_TENANT_ID}"
                        
                }
            }
        }
    }

    // Acciones a realizar después de que el pipeline finaliza
    post {
        always {
            echo "Pipeline finished."
        }
        failure {
            echo "Pipeline failed. Check logs for details."
            // Aquí podrías añadir notificaciones por correo, Slack, etc.
        }
        success {
            echo "Pipeline succeeded. Application deployed to Azure App Service."
        }
    }
}
