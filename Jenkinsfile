pipeline {
    agent any
    stages {
        stage('Checkout project from Git') {
            steps {
                git branch: 'main', url: 'https://github.com/AmaniHadda/DevopsProject.git'
            }
        }
         stage('Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }
         stage('Maven Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('JUnit/Mockito') {
            steps {
                sh 'mvn test'
            }
            post{
                always{
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('Maven SONARQUBE') {
            steps {
                withSonarQubeEnv('SonarQubeServer'){
                sh 'mvn sonar:sonar'
            }}
        }
        stage('artifact construction'){
            steps{
                sh 'mvn package'
            }
        }
        stage('Publish to nexus'){
            steps{
                nexusArtifactUploader artifacts: [
                [
                    artifactId: 'DevOps_Project',
                    classifier: '',
                    file: 'target/DevOps_Project-2.1.jar',
                    type: 'jar'
                ]],
                credentialsId: 'nexus',
                groupId: 'tn.esprit',
                nexusUrl: '192.168.33.10:8081',
                nexusVersion: 'nexus3',
                protocol: 'http',
                repository: 'maven-releases',
                version: '2.1'
            }
        }
        stage('Building docker image'){
            steps{
                sh 'docker build -t amanihadda/springproject:tag123 .'
            }
        }
        stage('Deploy Dockerhub'){
            steps{
               script{
                    withDockerRegistry(credentialsId: 'docker_login') {
                    sh 'docker push amanihadda/springproject:tag123'
                    }
               }
            }
        }
        stage('docker compose'){
            steps{
               sh 'docker compose up -d'
            }
        }
    }
    post {
        success {
	        mail to: "amani.hadda@esprit.tn",
            subject: "Pipeline Backend Success ",
            body: "Welcome to Spring project Backend : Success on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}"
        }
	    failure {
            mail to: "amani.hadda@esprit.tn",
            subject: "Pipeline backend Failure",
            body: "Welcome to Spring project Backend : Failure on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL} "
            }
    }
}

