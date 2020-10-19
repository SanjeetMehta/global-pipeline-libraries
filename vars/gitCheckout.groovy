#!/usr/bin/env groovy

def call(body) {
    def args = [
        // general arguments
        branch: '',
        url:''
    ]

    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = args
    body()
    echo "INFO: ${args.serviceName}"
    pipeline{
    checkout([
        $class: 'GitSCM',
        branches: [[name:  args.branch ]],
        userRemoteConfigs: [[ url: args.url ]]
    ])
    }
}
