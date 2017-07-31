#!/usr/bin/env groovy

pipeline {
    agent any

    options {
        disableConcurrentBuilds()
        timestamps()
    }

    tools {
        nodejs 'node-7'
    }

    parameters {
        string( name: 'APP_BASE_URL',
                defaultValue: 'https://local.pcfdev.io/',
                description: 'App Base URL to run e2e tests against')

        string( name: 'BRANCH',
                defaultValue: 'master',
                description: 'Branch to find e2e tests')

        string( name: 'E2E_REPO',
                defaultValue: 'https://github.com/julie-ng/js-cidemo-frontend.git',
                description: 'Repository to find e2e tests')
    }

    stages {
        stage('checkout') {
            steps {
                script {
                    git url: params.E2E_REPO,
                        branch: params.BRANCH,
                        credentialsId: 'github'
                }
            }
        }

        stage('NPM Install') {
            steps {
                sh 'npm install'
            }
        }

        stage('E2E Tests') {
            environment {
                APP_BASE_URL = "$params.APP_BASE_URL"
            }

            steps {
                sh 'npm run e2e:prod'
            }
        }
    }
}
