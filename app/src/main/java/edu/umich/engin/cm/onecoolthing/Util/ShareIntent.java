package edu.umich.engin.cm.onecoolthing.Util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by jawad on 04/12/14.
 */
public class ShareIntent {
    // Share an url to Facebook. Note: FB doesn't allow setting default text anymore
    public static void shareToFacebook(Context context, String urlToShare) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        context.startActivity(intent);
    }

    // Share text and an url directly to TWitter
    public static void shareToTwitter(Context context, String tweetText, String tweetUrl) {
        // Helps direct to Twitter
        final String twitterUrl = "https://twitter.com/intent/tweet?text=%s&url=%s";

        // Format the url and create an intent with it
        String finalUrl =
                String.format(twitterUrl,
                        Utils.urlEncode(tweetText), Utils.urlEncode(tweetUrl));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));

    // Narrow down to official Twitter app, if available:
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }

        context.startActivity(intent);
    }

    // Share text and an url in the general Android fashion
    public static void shareToGeneral(Context context, String subject, String url) {
        // Create the intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // Add the url and subject to the intent
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

        // Finally, actually start the intent
        context.startActivity(Intent.createChooser(intent, "Share"));
    }
}
