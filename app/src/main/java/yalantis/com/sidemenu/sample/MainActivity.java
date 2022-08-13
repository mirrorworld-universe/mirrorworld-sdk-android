package yalantis.com.sidemenu.sample;

import android.animation.Animator;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.mirror.sdk.MirrorSDKJava;
import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;
import yalantis.com.sidemenu.sample.fragment.APIListFragment;
import yalantis.com.sidemenu.sample.fragment.MultiParaItemFragment;
import yalantis.com.sidemenu.sample.fragment.placeholder.MultiItemData;
import yalantis.com.sidemenu.sample.fragment.placeholder.PlaceholderContent;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MirrorSDKJava.getInstance().InitSDK(this);
        setContentView(R.layout.activity_main);
        ContentFragment contentFragment = ContentFragment.newInstance(R.drawable.content_music);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PARTY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }


    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceToWalletAPIs(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        // todo add data to dataSource
        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                101,"Get wallet address",
                "Get user's wallet address on solana",
                "GetWallet",
                null,null,null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(102,"Transfer SOL","Transfer SOL",
                        "Get","to_publickey","amount",
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(103,"Transfer Token","Transfer Token",
                        "Get","to_publickey","amount",
                        "token_mint","decimals",null,null));

        items.add(
                new MultiItemData.MultiItem(104,"Get wallet tokens","Get a wallet's tokens",
                        "Get",null,null,
                        null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(105,"Get Wallet Transactions","Get a wallet's transactions by filters",
                        "Get","limit","before",
                        null,null,null,null));

        MultiParaItemFragment contentFragment = MultiParaItemFragment.newInstance(this.res,items);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }


    private ScreenShotable replaceToNFTAPIs(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                31,"Mint New NFT",
                "Mint New NFT on Collection",
                "MintNFT",
                "collection_mint","name","symbol","url",null,null));
        items.add(new MultiItemData.MultiItem(
                32,"Mint New Top-level",
                "Mint New Top-level Collection.",
                "MintNFT",
                "name","symbol","url",null,null,null));
        items.add(new MultiItemData.MultiItem(
                33,"Mint New Lower-level",
                "Mint New Lower-level Collection.",
                "MintNFT",
                "collection_mint","name","symbol","url",null,null));
        items.add(new MultiItemData.MultiItem(
                34,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by mint addresses.",
                "FetchNFTs",
                "mint_addresses1","mint_addresses2",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                35,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by creator addresses.",
                "FetchNFTs",
                "creators1","limit","offset",null,null,null));
        items.add(new MultiItemData.MultiItem(
                36,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by update authority addresses.",
                "FetchNFTs",
                "update_authorities1","limit","offset",null,null,null));
        MultiParaItemFragment contentFragment = MultiParaItemFragment.newInstance(this.res,items);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }
    private ScreenShotable replaceToMarketAPIs(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                12,"Fetch single NFT's details",
                "Fetch single NFT's details by mint address.",
                "FetchNFT","mint address",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                37,"List NFT on the marketplace",
                "Fetch multiple NFTs data by update authority addresses.",
                "ListNFTs",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                38,"Update Listing of NFT",
                "Update Listing of NFT on the marketplace.",
                "ListNFTs",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                39,"Buy NFT",
                "Buy NFT on the marketplace.",
                "BuyNFT",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                40,"Cancel listing of NFT",
                "Cancel listing of NFT on the marketplace.",
                "CancelIt",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                41,"Transfer NFT",
                "Transfer NFT to another solana wallet.",
                "TransferNFT",
                "mint_address","to_wallet_address",null,null,null,null));


        items.add(new MultiItemData.MultiItem(
                42,"Fetch multiple NFTs","Fetch multiple NFTs data by owner addresses",
                "Fetch NFTs","owner","limit","offset",null,null,null
        ));

        items.add(new MultiItemData.MultiItem(
                43,"Fetch activity","Fetch activity of a single NFT",
                "Fetch activity","mint_address",null,null,null,null,null
        ));


        MultiParaItemFragment contentFragment = MultiParaItemFragment.newInstance(this.res,items);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }
    private ScreenShotable replaceToUserAPIs(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                11,"Query user info",
                "Query user info by email address.",
                "QueryUser","input your email"));
        APIListFragment contentFragment = APIListFragment.newInstance(this.res,items);


        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }
    private ScreenShotable replaceToAuthAPIs(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                1,"Login",
                "Call login page so that user can get refresh token.",
                "Login",null));
        items.add(new PlaceholderContent.PlaceholderItem(
                2,"Refresh auth token",
                "Get an access token by refresh token so that you can visit APIs.",
                "RefreshToken",null));
        items.add(new PlaceholderContent.PlaceholderItem(
                3,"Set app id",
                "Set app id of your project,you can get it on sdk's site.",
                "Set","app-id"));
        APIListFragment contentFragment = APIListFragment.newInstance(this.res,items);


        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }



    private ScreenShotable replacedWalletAPI(ScreenShotable screenShotable, int topPosition){
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();


        // add info

        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                90,"just test",
                "just test some info",
                "test","test"));
        APIListFragment contentFragment = APIListFragment.newInstance(this.res,items);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;

    }




    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;
            case ContentFragment.BUILDING:
                return replaceToAuthAPIs(screenShotable, position);
            case ContentFragment.BOOK:
                return replaceToUserAPIs(screenShotable, position);
            case ContentFragment.PAINT:
                return replaceToMarketAPIs(screenShotable,position);
            case ContentFragment.CASE:
                return replaceToNFTAPIs(screenShotable,position);
            case ContentFragment.SHOP:
                return replaceToWalletAPIs(screenShotable,position);
            case ContentFragment.MOVIE:
                return replacedWalletAPI(screenShotable,position);
                default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
