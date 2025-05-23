pipeline {
    agent any

    environment {
        // Estas variables ahora se pasarán directamente como -D al mvn,
        // o se usarán en el pom.xml para resourceGroup, appName, region.
        // SubscriptionId se pasa a través del archivo auth_file.
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
                // Utiliza configFileProvider para inyectar el archivo JSON de credenciales
                // 'azure-auth-json' es el ID de la "Custom file" que creaste en Jenkins
                configFileProvider([configFile(fileId: 'azure-auth-json', targetLocation: 'azureAuth.json')]) {
                    sh """
                        mkdir -p "${env.HOME}/.azure"
                        mv azureAuth.json "${env.HOME}/.azure/azureProfile.json"
                        ${MAVEN_HOME}/bin/mvn azure-webapp:deploy -X \\
                            -DresourceGroup=${AZURE_RESOURCE_GROUP} \\
                            -DappName=${AZURE_APP_NAME} \\
                            -Dregion="${AZURE_REGION}" \\
                            -Dazure.auth.type=auth_file \\
                            -Dazure.auth.file="${env.HOME}/.azure/azureProfile.json"
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
