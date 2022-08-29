pipeline {
    agent any

    environment {
        ARTIFACT_NAME = 'Myrestapi-0.0.1-SNAPSHOT.jar'
        AWS_S3_BUCKET = 'elb-echo'
        AWS_EB_APP_NAME = 'Myrestapi'
        AWS_EB_ENVIRONMENT = 'Myrestapi-env'
        AWS_EB_APP_VERSION = "${BUILD_ID}"
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/anilpatil46/Myrestapi.git'
                sh '/usr/bin/chmod 777 mvnw'
                sh './mvnw clean compile'
                // bat '.\\mvnw clean compile'
            }
        }
        stage('Test') {
            steps {
                //sh './mvnw test'
                sh 'echo "Done  test"'
                // bat '.\\mvnw test'
            }

            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('Publish') {
            steps {
                sh './mvnw package'
                // bat '.\\mvnw package'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                    sh 'aws --version'
                    // bat 'aws --version'
                    //sh 'aws iam get-user --user-name root'
                    // bat 'aws iam get-user'
                    sh 'aws configure set region us-east-1'
                    sh 'aws s3 cp ./target/*.jar s3://$AWS_S3_BUCKET/$ARTIFACT_NAME'
                    //sh 'aws elasticbeanstalk create-application --application-name $AWS_EB_APP_NAME --version $AWS_EB_APP_VERSION --source-bundle S3Bucket=$AWS_S3_BUCKET,S3Key=$ARTIFACT_NAME'
                    //sh 'aws elasticbeanstalk update-environment --application-name $AWS_EB_APP_NAME --version-label $AWS_EB_APP_VERSION --environment-name $AWS_EB_ENVIRONMENT'
                    sh 'aws elasticbeanstalk check-dns-availability --cname-prefix $AWS_EB_APP_NAME'
                    sh 'aws elasticbeanstalk create-application --application-name $AWS_EB_APP_NAME --description "Myrestapi.git application"'
                    sh 'aws elasticbeanstalk create-application-version --application-name $AWS_EB_APP_NAME --version-label $AWS_EB_APP_VERSION --source-bundle S3Bucket=$AWS_S3_BUCKET,S3Key=$ARTIFACT_NAME'
                    sh 'aws elasticbeanstalk create-configuration-template --application-name $AWS_EB_APP_NAME --template-name v1 --solution-stack-name "64bit Amazon Linux 2018.03 v3.4.0 running Tomcat 8 Java 8"'
                    sh 'aws elasticbeanstalk create-environment --cname-prefix $AWS_EB_APP_NAME --application-name $AWS_EB_APP_NAME --template-name v1 --version-label $AWS_EB_APP_VERSION --environment-name $AWS_EB_ENVIRONMENT --option-settings file://options.txt'
                    sh 'aws elasticbeanstalk describe-environments --environment-names $AWS_EB_ENVIRONMENT'
                }
            }
        }
    }
}