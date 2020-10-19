#!/usr/bin/env groovy

def call(body) {
    def args = [
        // general arguments
        branch: '',
        url:'',
        service:''
    ]

    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = args
    body()
    echo "INFO: ${args.service}"
    echo "INFO: ${args.branch}"

    pipeline {
        agent any
        tools {
            maven
            jdk
        }
        stages {
            stage('Git Checkout') {
                steps {
                    checkout([
                     $class: 'GitSCM',
                        branches: [[name:  args.branch ]],
                        userRemoteConfigs: [[ url: args.url ]]
                    ])
                }
            }
            stage ('Initialize') {
                steps {
                    sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
                }
            }

            stage ('Build') {
                steps {
                    sh 'mvn -Dmaven.test.failure.ignore=true install'
                }
                post {
                    success {
                        junit 'target/surefire-reports/**/*.xml'
                    }
                }
            }
        }
    }
}
