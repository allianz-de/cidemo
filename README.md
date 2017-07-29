# Full Stack JavaScript CI/CD Demo

## Toolchain

Most of the toolchain uses open source software and is included in the [docker-compose.yml](./docker-compose.yml) file.

### Jenkins

Our Automation server

### Selenium Hub

Provides Chrome and Firefox browsers for end-to-end testing

### Artifactory

For managing build artifacts

### Pivotal Cloud Foundry

The cloud application platform where we will deploy our applications. For the demo, we will be running a local copy of [PCF Dev](https://pivotal.io/pcf-dev). Unlike the rest of the toolchain, you need to [install pcf dev separately](https://docs.pivotal.io/pcf-dev/index.html).

Once installed login at [https://apps.local.pcfdev.io/](https://apps.local.pcfdev.io/)
