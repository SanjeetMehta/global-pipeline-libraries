#!/usr/bin/env groovy

def call(body) {
    def args = [
        // general arguments
        serviceName: ''
    ]

    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = args
    body()
    echo "INFO: ${args.serviceName}"
    pipeline {
        triggers {
            pollSCM("H/5 6-20 * * 1-5")
        }
    }
}
