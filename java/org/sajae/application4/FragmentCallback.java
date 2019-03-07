package org.sajae.application4;

import android.os.Bundle;

public interface FragmentCallback {
    public void onFragmentSelected(int position);
    public void showCommentWriteActivity();
    public void showCommentSeeActivity();
}
