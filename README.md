# kotlin-backstack

Backstack for Model-View-Presenter (MVP) _Views_ in a Fragment-less design, written in Kotlin.

## Why?

With an MVP architecture, you may want to do away with Android's Fragments, keep Activities to a minimum,
and have clean Models, Views and Presenters.
To manage your Views, some kind of backstack is required...


## How do I use this library?

### 1. `git clone` this library to your machine

Put this somewhere easily-accessible.


### 2. Add the library to your project

This can be done in one of two ways:

#### As a _library_ module

- a. In Android Studio, right-click your project in the 'Project' tab, and select _Open Module Settings_ ![Module Settings](module_settings.PNG "Module settings window")
- b. Click the + (circled) to add a module ![Module import](module_import.PNG "Module import window")
- c. In the _Create New Module_ window, under _Import..._, set the _Source Directory_ to the **inner `backstack` module within the saved library project** (i.e. `backstack/backstack`)


#### .aar file

- a. Copy `backstack/backstack/build/outputs/aar/backstack-release.aar` 
- b. Paste to `<app-name>/<app-module>/libs/`
- c. Ensure your app module's Gradle _build script_ imports `backstack-release.aar`:

Gradle _build configuration scripts_ are written in either Groovy DSL or Kotlin DSL (domain-specific language).

If Kotlin DSL (`build.gradle.kts`):

```kotlin
dependencies {
    implementation(files("libs/backstack-release.aar"))
}
```

If Groovy DSL (`build.gradle`):

```groovy
dependencies {
    implementation files('libs/backstack-release.aar')
}
```


### 3. Setup your Activity 

Ensure your Activity implements `BackStack.ActivityInterface`.

Below is a guide of an Activity implementation: you can view a working example in the `backstackexample` module of this project.

```kotlin
class MainActivity : AppCompatActivity(), BackStack.ActivityInterface {
    private lateinit var binding: ActivityMainBinding
    private val backstack = BackStack(this)
    
    /**
     * Notes:
     * - View Binding (https://developer.android.com/topic/libraries/view-binding) is in-use
     * - Content set, view binding, and Model object creation should be done before call 
     * to `backstack.onCreate()`.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = Model()
        backstack.onCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backstack.goBack()
    }

    override fun getInitialView(): BackStackView {
        return ExampleView()
    }

    override fun addView(view: ViewGroup) {
        binding.container.addView(view)
    }

    override fun removeAllViews() {
        binding.container.removeAllViews()
    }

    /**
     * I have opted to create the Model in onCreate().
     */
    override fun getModel(): ModelInterface {
        return model
    }

    override fun getContext(): Context {
        return this
    }
}
```

### 4. Setup your view(s)

Assumption: you already have an MVP architecture setup for this application - if not, see the code in `backstackexample` for an example:

```kotlin
class ExampleView: BackStackView() {
    private lateinit var presenter: ExamplePresenterInterface
    private lateinit var binding: ExampleViewBinding

    /********** public */
    
    override fun getLayout(): Int = R.layout.example_view

    override fun onCreate(backstack: BackStackInterface, model: ModelInterface,
                          context: AndroidContextInterface) {
        binding = ViewBinding.bind(view!!)
        setupButtonOnClickListener()
        /** This should be last as Presenter will likely use views defined above. **/
        this.presenter = ExamplePresenter(this, model as ExampleModelInterface, backstack)
    }

    override fun onResume(context: AndroidContextInterface) {
        binding = ViewBinding.bind(view!!)
        this.presenter.onResume()
    }
}
```


## Notes:

- I have not tested any kind of compatibility with [Jetpack Compose](https://developer.android.com/jetpack/compose).