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
                sh "mvn clean install -DskipTests"
            }
        }

        stage('Deploy to Azure App Service') {
            steps {
                configFileProvider([configFile(fileId: 'azure-auth-json', targetLocation: 'azureAuth.json')]) {
                    sh """
                        mvn azure-webapp:deploy \
                            -Dazure.auth=file \
                            -Dazure.auth.file=azureAuth.json \
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
