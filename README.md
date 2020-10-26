# GitHubRepositories
Authorization with GitHub and search for repositories.

*(still in development)*

**Initial requirements:**
- Not block screen while searching
- Show previous results *(if exists)*
- Sort by stars
- Cut long description *(30 chars max)*
- Open browser by click on an item

**TODO:**
- Use user key for request headers.
- Pagination.
- Add *"Viewed"* mark for items previously opened
- Cancel active search

**Possible features:**
- Filter by languages
- Different sort types
- Implement Google pagination library instead of scroll listener

**Helpful links:**  
https://developer.github.com/v3  
https://github.com/firebase/FirebaseUI-Android/tree/master/auth

*Also you can find an additional step to setup firebase hosting properly here:*  
https://stackoverflow.com/questions/55636754

**Tech stack:**
- Kotlin;
- Clean Architecture, MVVM (AAC);
- LiveData, Room;
- Dagger 2.17+
- Retrofit, Moshi
- RxJava2, RxAndroid
- Firebase, Firebase UI;

**After [commit](https://github.com/AlexeyMerov/GitHubRepositories/commit/1ef3567e8c66391b35ca63504b75c3360da16aaf):**
- Dagger -> Hilt
- Rx -> Coroutines / Flow
- Kotlin Synthetic -> View Binding
- MVVM + MVI