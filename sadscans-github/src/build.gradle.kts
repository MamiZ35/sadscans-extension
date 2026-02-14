apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'

ext {
    extName = 'Sadscans'
    pkgNameSuffix = 'tr.sadscans'
    extClass = '.Sadscans'
    extVersionCode = 1
    libVersion = '1.5'
}

dependencies {
    compileOnly project(':core')
}

apply from: "$rootDir/common.gradle"
