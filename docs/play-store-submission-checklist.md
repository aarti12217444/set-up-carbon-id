# Google Play Submission Checklist

## 1) Legal Pages (GitHub Pages)
- [ ] Privacy Policy page live
- [ ] Terms and Conditions page live
- [ ] Delete Account page live (required if app has account creation)
- [ ] Support page live

## 2) App Security
- [ ] Gemini API key rotated (old exposed key revoked)
- [ ] API key restrictions configured (Android app restriction + API restriction)
- [ ] Firebase security rules reviewed (Auth + Realtime Database)
- [ ] Release build tested (no debug-only behavior)

## 3) Store Listing Content
- [ ] App title
- [ ] Short description
- [ ] Full description
- [ ] Feature graphic
- [ ] App icon
- [ ] Screenshots (phone and optional tablet)
- [ ] Contact email
- [ ] Privacy Policy URL

## 4) Policy and Compliance
- [ ] Data Safety form completed correctly
- [ ] Permission declarations reviewed
- [ ] If ACTIVITY_RECOGNITION is used, explain clearly in policy and app description
- [ ] If account creation exists, provide account deletion URL

## 5) Technical Release
- [ ] Signed release AAB generated
- [ ] versionCode incremented
- [ ] versionName updated
- [ ] Proguard/R8 rules validated (if minify enabled)
- [ ] Crash-free smoke test on 2-3 real devices

## 6) Important Notes for Current Project
- [ ] Replace placeholder values in docs pages (developer name, support email, address)
- [ ] Remove invalid permission name SENSOR_ACCELEOMETER from AndroidManifest (not a valid Android permission)
- [ ] Verify only required permissions remain

## 7) Publish Flow
- [ ] Internal testing track upload
- [ ] Closed testing (if required for your Play account)
- [ ] Production rollout (staged recommended)
