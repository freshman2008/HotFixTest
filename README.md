Tinker patch生成方式：
1.使用命令行的方式完成patch包的生成
    java -jar tinker-patch-cli-1.7.7.jar -old old.apk -new new.apk -config tinker_config.xml -out output_path

2.使用gradle插件的方式完成patch包的生成（gradle中正确的配置完之后，通过gradle命令去生成）
     2.1配置build.gradle脚本
     2.2在Terminal中执行 gradlew assembleRelease生成基准apk文件到build/bakApk中，并把文件信息更新到build.gradle中(tinkerOldApkPath，tinkerApplyMappingPath，tinkerApplyResourceMappingPath等信息需要更新)
     2.3执行tinkerPatchRelease任务生成patch文件