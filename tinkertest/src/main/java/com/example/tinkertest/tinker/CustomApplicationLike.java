package com.example.tinkertest.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import androidx.multidex.MultiDex;

/**
 * Tinker集成第三步，定义CustomApplicationLike，完成Tinker初始化，通过注解生成Application
 *
 * 通过ApplicationLike对象帮助我们生成Application对象
 * 为什么需要ApplicationLike这个对象，而不直接在我们应用的Application中完成Tinker的初始化?
 * 是因为：Tinker需要监听我们Application对象的生命周期，所以它通过ApplicationLike对象进行一个委托
 * 通过这个委托它可以在ApplicationLike中完成对Tinker的生命周期的监听，然后在不同的Application生命周
 * 期阶段去做不同的初始化工作也好，等等一些其他工作。
 * 如果Tinker没有使用这种委托的模式，那么整个Tinker的初始化会非常的复杂，而且需要我们开发者自己动手去做，
 * 而通过这种代理的方式呢，它就会把所有的工作都自己封装到ApplicationLike对象中，而开发者需要做的就是仅仅调用这个初始化方法即可
 * 所以Tinker提供ApplicationLike对象，从根本上并不是为了让我们的应用变得复杂让我们使用更复杂，而只是让我们通过一个小小的委托，可以让Tinker
 * 使用起来更加简单。以上就是ApplicationLike类的作用。
 *
 * 通过DefaultLifeCycle注解，来生成我们应用中要使用的Application类，而MyTinkerApplication类就是相当于我们平时在开发应用中自定义的那个Application
 * 它呢需要我们在AndroidManifest文件中声明一下
 *
 */
@DefaultLifeCycle(application = ".MyTinkerApplication", flags = ShareConstants.TINKER_ENABLE_ALL, loadVerifyFlag = false)
public class CustomApplicationLike extends ApplicationLike {
    public CustomApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

        //使应用支持分包(如果应用不支持分包，可以不调用)
        MultiDex.install(base);

        TinkerManager.installTinker(this);
    }
}
