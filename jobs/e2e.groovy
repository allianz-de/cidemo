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
        string( name: 'APP_URL',
                defaultValue: 'https://app-url.local.pcfdev.io/',
                description: 'App URL to run e2e tests against')

        string( name: 'BRANCH',
                defaultValue: 'master',
                description: 'Branch to find e2e tests')

        string( name: 'E2E_REPO',
                defaultValue: 'https://github.com/AllianzDeutschlandAG/cidemo-frontend.git',
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
                APP_URL = "$params.APP_URL"
            }

            steps {
                sh 'npm run e2e:prod'
            }
        }

        stage('merge into master') {
            environment {
                GHE = credentials('github-enterprise')
            }

            steps {
                sh "git checkout master"
                sh "git config user.name 'technical user'"
                sh "git config user.email 'jenkins@allianz.de'"
                sh "git merge ${params.BRANCH}"
                sh "git push https://${env.GHE_USR}:${env.GHE_PSW}@ghe.adp.allianz/org/repo"
            }
        }
    }
}
