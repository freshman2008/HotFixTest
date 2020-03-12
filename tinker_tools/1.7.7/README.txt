1.创建目录tinker_tool用于存放工具文件

2.下载tinker工程https://github.com/Tencent/tinker.git
	2.1把tinker-build\tinker-patch-cli\tool_output目录下的文件拷贝到tinker_tool目录下
	2.2编译tinker-build模块生成tinker-patch-cli-1.x.x.jar文件，拷贝到tinker_tool目录下
	

3.tinker_config.xml文件修改
	3.1首先需要修改dex域
	<issue id="dex">
		......
		<loader value="com.example.tinkertest.tinker.MyTinkerApplication"/>
	</issue>
	把loader value修改为自己实际项目中的Application全名，就是tinker生成的Application类
	
	3.2其次修改
	<issue id="sign">
		<!--the signature file path, in window use \, in linux use /, and the default path is the running location-->
		<path value="mytest.jks"/>
		<!--storepass-->
		<storepass value="test123456"/>
		<!--keypass-->
		<keypass value="test123456"/>
		<!--alias-->
		<alias value="key0"/>
	</issue>
	修改成对应的签名文件与密码

4.执行命令生成补丁文件
java -jar tinker-patch-cli-1.7.7.jar -old old.apk -new new.apk -config tinker_config.xml -out output_path

--------------------------------------------------------------------------------------------------------------------
C:\Users\lixiulia\Videos\tinker_tools\1.7.7>java -jar tinker-patch-cli-1.7.7.jar -old old.apk -new new.apk -config tinker_config.xml -out output_path
special configFile file path:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\tinker_config.xml
special output directory path: C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path
reading config file, C:\Users\lixiulia\Videos\tinker_tools\1.7.7\tinker_config.xml
-----------------------Tinker patch begin-----------------------
configuration:
oldApk:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\old.apk
newApk:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\new.apk
outputFolder:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path
isIgnoreWarning:false
7-ZipPath:7za
useSignAPk:true
package meta fields:
filed name:patchMessage, filed value:classes.dex
filed name:platform, filed value:all
dex configs:
dexMode: jar
dexPattern:classes.*\.dex
dexPattern:assets/secondary-dex-.\.jar
dex loader:com.example.tinkertest.tinker.MyTinkerApplication
dex loader:com.tencent.tinker.loader.*
lib configs:
libPattern:lib/armeabi/.*\.so
resource configs:
resPattern:res/.*
resPattern:resources\.arsc
resPattern:AndroidManifest\.xml
resPattern:assets/.*
resIgnore change:assets/sample_meta\.txt
largeModSize:100kb
useApplyResource:false

Analyze old and new apk files:
old apk: old.apk, size=970009, md5=6b1ef7254a6768c5dc686da616e18f16
new apk: new.apk, size=970060, md5=c452c2d9713022b7b82e5224fa8651af

UnZipping apk to C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\old
UnZipping apk to C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\new
Check for loader classes in dex: classes.dex
Check for loader classes in dex: classes2.dex
Found modify resource: res/layout/abc_activity_chooser_view.xml
Found modify resource: res/layout/abc_activity_chooser_view_list_item.xml
Found modify resource: res/layout/abc_alert_dialog_button_bar_material.xml
Found modify resource: res/layout/abc_alert_dialog_material.xml
Found modify resource: res/layout/abc_alert_dialog_title_material.xml
Found modify resource: res/layout/abc_cascading_menu_item_layout.xml
Found modify resource: res/layout/abc_dialog_title_material.xml
Found modify resource: res/layout/abc_expanded_menu_layout.xml
Found modify resource: res/layout/abc_list_menu_item_checkbox.xml
Found modify resource: res/layout/abc_list_menu_item_icon.xml
Found modify resource: res/layout/abc_list_menu_item_layout.xml
Found modify resource: res/layout/abc_list_menu_item_radio.xml
Found modify resource: res/layout/abc_popup_menu_item_layout.xml
Found modify resource: res/layout/abc_screen_toolbar.xml
Found modify resource: res/layout/abc_search_dropdown_item_icons_2line.xml
Found modify resource: res/layout/abc_search_view.xml
Found modify resource: res/layout/abc_select_dialog_material.xml
Found modify resource: res/layout/abc_tooltip.xml
Found modify resource: res/layout/activity_main.xml
Found modify resource: res/layout/notification_template_icon_group.xml
Found modify resource: res/layout/notification_template_part_chronometer.xml
Found modify resource: res/layout/notification_template_part_time.xml
Found modify resource: res/layout-v16/notification_template_custom_big.xml
Found modify resource: res/layout-v21/abc_screen_toolbar.xml
Found modify resource: res/layout-v21/notification_template_custom_big.xml
Found modify resource: res/layout-v21/notification_template_icon_group.xml
Found modify resource: res/layout-v22/abc_alert_dialog_button_bar_material.xml
Found modify resource: res/layout-v26/abc_screen_toolbar.xml
Found modify resource: res/layout-watch-v20/abc_alert_dialog_button_bar_material.xml
Found modify resource: res/layout-watch-v20/abc_alert_dialog_title_material.xml
ApkParser: resources.arsc is not equal, reason: file size is changed
Found large modify resource: resources.arsc size:263460

Gen classes2.dex patch file:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\tinker_result\classes2.dex, size:885, md5:64255d4f79d9518516b001570481300f
Verifying if patched new dex is logically the same as original new dex: new/classes2.dex ...

Gen classes2.dex for dalvik full dex file:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\tempPatchedDexes\classes2.dex, size:721316, md5:ba4ac93119a1b45d4337aeb20e53bd9d

Do additional diff on main dex to remove loader classes in it.

Gen classes.dex patch file:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\tinker_result\classes.dex, size:151, md5:59ac1e9bd7d3690b9791bdbaa427891e
Verifying if patched new dex is logically the same as original new dex: new/classes.dex ...

Gen classes.dex for dalvik full dex file:C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\tempPatchedDexes\classes.dex, size:14120, md5:b1b85d31eb9079076d98bda7afc6e61e
DexDecoder:write meta file data: classes.dex,,b1b85d31eb9079076d98bda7afc6e61e,b1b85d31eb9079076d98bda7afc6e61e,59ac1e9bd7d3690b9791bdbaa427891e,3994815340,jar
DexDecoder:write meta file data: classes2.dex,,ba4ac93119a1b45d4337aeb20e53bd9d,ba4ac93119a1b45d4337aeb20e53bd9d,64255d4f79d9518516b001570481300f,1354693958,jar

Add test install result dex: C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\tinker_result\test.dex, size:584
DexDecoder:write test dex meta file data: test.dex,,56900442eb5b7e1de45449d0685e6e00,56900442eb5b7e1de45449d0685e6e00,0,0,jar
Add Test resource file: assets/only_use_to_test_tinker_resource.txt
Final normal zip resource: resources_out.zip, size=577482, md5=aa060f757550849f95a31112bad79873
7a patch file failed, you should set the zipArtifact, or set the path directly
Generate unsigned apk: patch_unsigned.apk
Signing apk: patch_signed.apk
Signing key algorithm is SHA1withRSA
Try use 7za to compress the patch file: patch_signed_7zip.apk, will cost much more time
Current 7za path:7za
7a patch file failed, you should set the zipArtifact, or set the path directly
Result: final signed patch result: C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path\patch_signed.apk, size=29888
Tinker patch done, total time cost: 6.647000s
Tinker patch done, you can go to file to find the output C:\Users\lixiulia\Videos\tinker_tools\1.7.7\output_path
-----------------------Tinker patch end-------------------------
--------------------------------------------------------------------------------------------------------------------
