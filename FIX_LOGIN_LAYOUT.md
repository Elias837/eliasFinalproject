# MANUAL FIX REQUIRED: activity_login.xml

## Problem: Empty TextInputLayout containers causing NullPointerException

## Fix: Add missing TextInputEditText elements

### 1. Email Field (around lines 10-21):
REPLACE:
```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/et_email_wrapper"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent">

</com.google.android.material.textfield.TextInputLayout>
```

WITH:
```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/et_email_wrapper"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/et_email"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Email"
        android:inputType="textEmailAddress" />
</com.google.android.material.textfield.TextInputLayout>
```

### 2. Password Field (around lines 23-34):
REPLACE:
```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/et_password_wrapper"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/et_email_wrapper">

</com.google.android.material.textfield.TextInputLayout>
```

WITH:
```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/et_password_wrapper"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/et_email_wrapper">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/et_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword" />
</com.google.android.material.textfield.TextInputLayout>
```

## Remove Duplicate Elements:
Also remove the duplicate email/password fields that are positioned separately (lines 79-101) as they're now properly nested inside the TextInputLayout containers.
