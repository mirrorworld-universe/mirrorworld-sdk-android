# MarketSDK安卓接入文档

最新版本为: 0.1.0
更新时间为: 2022-08-15

## 导入前准备
1. 在[开发者后台](https://app.mirrorworld.fun/)创建一个开发者账号；
2. 在开发者后台创建一个项目并保存它的API Key，以便在后续的接入流程中使用。


## 导入SDK
*提示
Android SDK 要求最低系统版本为 Android 4.4
Android SDK ( aar 格式) 大小约为 16 KB*
1. 下载并解压 [Android SDK](www.baidu.com)
2. libs 文件夹(如果目录下没有则需手动创建）中添加 MirrorSDK.aar
   ![image](https://internal-api-drive-stream.feishu.cn/space/api/box/stream/download/v2/cover/boxcnm7QhYooAjDdWrAZYp8zB2e/?fallback_source=1&height=1280&mount_node_token=doxcnIIeWAQW8ESeiGush255kXd&mount_point=docx_image&policy=equal&width=1280)
3. 在 build.gradle 添加如下配置
   dependencies {
   implementation fileTree(dir: 'libs', include: ['*.jar','*.aar'])
   }

## 初始化SDK
1. 设置ApiKey

在MirrorSDK中，使用MirrorSDK单例来管理所有SDK的方法:  
在Java中，请使用MirrorSDKJava.getInstance()来获得单例句柄；
在Kotlin中，请使用MirrorSDK.instance来获得单例句柄。
在调用其他API前，调用SetAppID方法来设置App的AppID。
```
MirrorSDK.Companion.get().SetAppID(mContext,"YourAppID");
```

2. 初始化SDK

创建 LogtoClient 前，需要确保 Application 已经初始化。
调用InitSDK(Context)方法对SDK进行初始化，并传入上下文参数。
```
MirrorSDKJava.getInstance().InitSDK(this);
```

3. 引导用户登录

要使用MirrorSDK提供的接口服务，需要先引导用户进行登录流程以便SDK能够获取token。
建议将登录操作纳入目标应用，并作为用户登录应用的必要步骤之一。  
调用StartLogin方法开始SDK的登录流程。
```
MirrorSDK.Companion.get().StartLogin(mContext);
```
在之后的与SDK的互动中，如果用户没有正确地进行过登录，将会自动弹出登录界面引导用户登录（但这对用户是不友好的，强烈建议将登录流程集成入App的使用流程中）。

4. 调用MirrorSDK提供的服务接口
   当完成以上步骤后，即可直接调用SDK提供的各个接口获取服务。
   附：SDK接口列表。

# Android API Reference
## Authentication Methods
### StartLogin
调用这个接口将会在Android端弹出一个Dialog，引导用户去完成登录流程。在这个页面，用户可选择谷歌、Discord及邮箱等登录手段去完成登录。
```
MirrorSDKJava.getInstance().StartLogin();
```


### StartLoginWithCallback
与StartLogin方法功能相同，但使用StartLoginWithCallback，开发者可以传入回调以便监听登录完成消息。

```
MirrorSDKJava.getInstance().StartLoginWithCallback(new MirrorCallback() {
    @Override
    public void callback(String result) {
        Log.i("MyApp","Login flow successed!");
    }
});
```

### SetDebug
设置是否使用Debug模式。在Debug模式下，开发者能够看到SDK的具体工作流程。
```
MirrorSDKJava.getInstance().SetDebug(true);
```

### SetAppID
设置当前所使用的ApiKey，你能够在开发者后台找到它。
```
String apiKey = "Your api key";
MirrorSDKJava.getInstance().SetAppID(apiKey);
```

## Wallet Methods
### APIGetWalletAddress
获取钱包地址
```
MirrorSDKJava.getInstance().APIGetWalletAddress(new MirrorCallback() {
    @Override
    public void callback(String result) {
        Log.i("MyApp","my wallet response is xxxx");
    }
});
```

### GetAccessToken
获取AccessToken以便访问接口。通常情况下，SDK会自动在缺乏授权的情况下去自动访问这个接口，开发者无需手动调用。
```
Activity activity;
MirrorSDKJava.getInstance().GetAccessToken(activity);
```

### APIQueryUser
查询用户信息，其中包含用户的钱包地址等基本信息。
```
String email = "youremail@xxx.com";
MirrorSDKJava.getInstance().APIQueryUser(email, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","User info is:"+s);
    }
});
```

## Marketplace Methods
### APIFetchSingleNFT
获取单个NFT的详细信息。
```
String mintAddress = "nft_mint_address";
MirrorSDKJava.getInstance().APIFetchSingleNFT(mintAddress, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","nft info is:"+s);
    }
});
```

### APIMintNewNFTOnCollection
铸造一个新的NFT
```
String collectionAddress = "collection_address";
String mintName = "NewNFT";
String mintSymbol = "Symbol";
String metaDataUri: "https://my-collection-metadata-uri/metadata.json";

MirrorSDKJava.getInstance().APIMintNewNFTOnCollection(collectionAddress,mintName, mintSymbol, metaDataUri, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","New nft is:"+s);
    }
});
```

### APIMintNewTopLevelCollection
铸造一个顶级Collection
```
String mintName = "NewNFT";
String mintSymbol = "Symbol";
String metaDataUri: "https://my-collection-metadata-uri/metadata.json";

MirrorSDKJava.getInstance().APIMintNewTopLevelCollection(mintName, mintSymbol, metaDataUri, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","New collection is:"+s);
    }
});
```

### APIMintNewLowerLevelCollection
铸造一个低级Collection
```
String collectionAddress = "collection_address";
String mintName = "NewNFT";
String mintSymbol = "Symbol";
String metaDataUri: "https://my-collection-metadata-uri/metadata.json";

MirrorSDKJava.getInstance().APIMintNewLowerLevelCollection(collectionAddress,mintName, mintSymbol, metaDataUri, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","New collection is:"+s);
    }
});
```

### APITransferNFTToAnotherSolanaWallet
将NFT转移给其他Solana钱包
```
String mintAddress = "mint_address";
String toWalletAddress = "wallet_address";

MirrorSDKJava.getInstance().APITransferNFTToAnotherSolanaWallet(mintAddress,toWalletAddress, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","Transfer result is:"+s);
    }
});
```

### APICancelListingOfNFT
取消NFT列表
```
String mintAddress = "mint_address";
Double price = 123.99;

MirrorSDKJava.getInstance().APICancelListingOfNFT(mintAddress,price, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","result is:"+s);
    }
});
```

### APIBuyNFT
购买NFT
```
String mintAddress = "mint_address";
Double price = 123.99;

MirrorSDKJava.getInstance().APIBuyNFT(mintAddress,price, new MirrorCallback() {
    @Override
    public void callback(String s) {
        Log.i("MyApp","result is:"+s);
    }
});
```

### APIUpdateListingOfNFT
更新NFT列表
```
String mintAddress = "mint_address";
Double price = 123.99;

MirrorSDKJava.getInstance().APIUpdateListingOfNFT(mintAddress,price, new MirrorCallback() {
    @Override
    public void callback(String s) {
        holder.mResultView.setText(s);
    }
});
```

### APIListNFTOnTheMarketplace
获取Marketplace中的NFT列表
```
String mintAddress = "mint_address";
Double price = 123.99;

MirrorSDKJava.getInstance().APIListNFTOnTheMarketplace(mintAddress,price, new MirrorCallback() {
    @Override
    public void callback(String s) {
        holder.mResultView.setText(s);
    }
});

```

### APIFetchNFTsByUpdateAuthorityAddress
获取NFT集合，通过AuthorityAddress
```
List<String> list = new ArrayList<>();
list.add("authority_address_1");
list.add("authority_address_2");
Double limit = 2;
Double offset = "0.23";

MirrorSDKJava.getInstance().APIFetchNFTsByUpdateAuthorityAddress(list,limit,offset, new MirrorCallback() {
    @Override
    public void callback(String s) {
        holder.mResultView.setText(s);
    }
});
```

### APIFetchNFTsByCreatorAddress
获取NFT集合，通过CreatorAddress
```
List<String> list = new ArrayList<>();
list.add("authority_address_1");
list.add("authority_address_2");
Double limit = 2;
Double offset = "0.23";

MirrorSDKJava.getInstance().APIFetchNFTsByCreatorAddress(list,limit,offset, new MirrorCallback() {
    @Override
    public void callback(String s) {
        holder.mResultView.setText(s);
    }
});
```

### APIFetchNFTsByMintAddress
获取NFT集合，通过Mint Address
```
List<String> list = new ArrayList<>();
list.add("mint_address_1");
list.add("mint_address_2");

MirrorSDKJava.getInstance().APIFetchNFTsByMintAddress(list, new MirrorCallback() {
    @Override
    public void callback(String s) {
        holder.mResultView.setText(s);
    }
});
```

# Authentication注意事项

## 存储用户登录信息
当用户首次登录成功后，开发者可为其在本地存储一个登录成功的flag，代表用户无需再次登录即可直接调用SDK其他接口。

## 使用不同的社交账号登录
每一种社交账号的登录都将自动为用户绑定一个新的钱包，他们之间是不互通的。

## 长期不登录导致的Token过期
当用户长时间没有登录App，SDK为他存储在本地的token可能会过期。不必担心这种情况，当SDK遇到无法使用的token时，会自动弹出对话框，引导用户进行重新登录。  