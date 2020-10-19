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
        }
    }
}
