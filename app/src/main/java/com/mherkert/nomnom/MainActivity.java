package com.mherkert.nomnom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mherkert.nomnom.domain.ActivityResult;
import com.mherkert.nomnom.domain.Recipes;
import com.mherkert.nomnom.fragments.NavigationDrawerFragment;
import com.mherkert.nomnom.fragments.RecipeAddDialogFragment;
import com.mherkert.nomnom.fragments.RecipeAddFragment;
import com.mherkert.nomnom.fragments.RecipeFragment;
import com.mherkert.nomnom.fragments.RecipesFragment;
import com.mherkert.nomnom.parser.RecipesJsonParser;
import com.mherkert.nomnom.utils.FileUtils;

import java.io.File;
import java.io.IOException;


public class MainActivity extends LifecycleLoggingActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, RecipesFragment.RecipeFragmentCallbacks,
        RecipeAddDialogFragment.RecipeAddDialogFragmentCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_PHOTO_CAPTURE = 1;
    private static final int REQUEST_PHOTO_SELECT = 2;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Recipes mRecipes;

    private Uri mCurrentPhotoUri;
    private ActivityResult mActivityResult;

    private RecipesFragment mRecipesFragment;
    private RecipeFragment mRecipeFragment;
    private RecipeAddFragment mRecipeAddFragment;

    private RecipesJsonParser parser = new RecipesJsonParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        // TODO retain data
        String text = FileUtils.loadRecipesFromFile();
//        String text = FileUtils.loadData(this, R.raw.recipes);
        mRecipes = new Recipes(parser.toDomain(text));

        // TODO retain instances?
        mRecipesFragment = RecipesFragment.newInstance(mRecipes);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mRecipesFragment)
                .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Persist recipes. Number of items: " + mRecipes.getRecipes().size());
        FileUtils.writeDataToFile(parser.toJson(mRecipes.getRecipes()));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecipeItemSelected(int position) {
        Log.d(TAG, "Selected recipe item at position " + position);
        if (mRecipeFragment == null)
            mRecipeFragment = new RecipeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, mRecipeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        mRecipeFragment.updateRecipeDisplay(this, mRecipes.getRecipes().get(position));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mActivityResult != null) {
            int requestCode = mActivityResult.getRequestCode();
            int resultCode = mActivityResult.getResultCode();
            Intent data = mActivityResult.getData();
            if (requestCode == REQUEST_PHOTO_CAPTURE) {
                if (resultCode == RESULT_OK) {
                    if (mRecipeAddFragment == null)
                        mRecipeAddFragment = new RecipeAddFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, mRecipeAddFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    fragmentManager.executePendingTransactions();

                    mRecipeAddFragment.updateRecipeAddDisplay(this, mCurrentPhotoUri);

                } else if (resultCode == RESULT_CANCELED) {
                    // if cancelled, delete the current temporary file

                    if (mCurrentPhotoUri != null) {
                        FileUtils.deleteFile(mCurrentPhotoUri.getPath());
                    }
                }
            } else if (requestCode == REQUEST_PHOTO_SELECT) {
                if (resultCode == RESULT_OK) {
                    if (mRecipeAddFragment == null)
                        mRecipeAddFragment = new RecipeAddFragment();

                    // TODO copy photo first?
                    mCurrentPhotoUri = data.getData();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, mRecipeAddFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    fragmentManager.executePendingTransactions();

                    mRecipeAddFragment.updateRecipeAddDisplay(this, mCurrentPhotoUri);
                }
            }
        }

        mActivityResult = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mActivityResult = new ActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAddRecipeFromImageSelected() {

        RecipeAddDialogFragment recipeAddDialogFragment = new RecipeAddDialogFragment();
        recipeAddDialogFragment.show(getSupportFragmentManager(), "RecipeAddDialogFragment");

//        TesseractOcrImageReader imageReader = new TesseractOcrImageReader();
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/high_res.jpg");
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/high_res_Fotor.jpg");
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/high_res_filter.png");
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/high_res_filter.jpg");
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/high_res_Fotor.png");
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/ingredients.jpg");
//        String s = imageReader.imageToText("/storage/sdcard/Documents/com.mherkert.nomnom/harissa_recipe_new.png");
//        Log.d(TAG, s);
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddRecipeFromTextSelected() {
        Log.d(TAG, "Attach AddRecipe fragment to add new recipe.");
        Toast.makeText(this, "Open New Recipe TextEdit", Toast.LENGTH_SHORT).show();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void dispatchChoosePhotoIntent() {
        Log.d(TAG, "Open gallery to choose a photo.");

        Intent choosePhotoIntent = new Intent();
        choosePhotoIntent.setType("image/*");
        choosePhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(choosePhotoIntent,
                "Select Photo"), REQUEST_PHOTO_SELECT);
    }


    private void dispatchTakePhotoIntent() {
        Log.d(TAG, "Open camera to take new photo.");
        if (!isExternalStorageWritable())
            showStorageNotAvailableError();
        else {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                // Create the file where the photo should be stored
                File image = null;
                try {
                    image = FileUtils.createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the file was successfully created
                if (image != null) {
                    mCurrentPhotoUri = Uri.fromFile(image);
                    Log.d(TAG, "Save temporary recipe picture at " + mCurrentPhotoUri);
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                    startActivityForResult(takePhotoIntent, REQUEST_PHOTO_CAPTURE);
                }
            }
        }
    }

    private void showStorageNotAvailableError() {
        Toast.makeText(this, R.string.error_no_storage_available, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogOpenCameraClick() {
        dispatchTakePhotoIntent();
    }

    @Override
    public void onDialogOpenGalleryClick() {
        dispatchChoosePhotoIntent();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
