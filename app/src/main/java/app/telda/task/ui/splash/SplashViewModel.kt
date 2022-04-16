package app.telda.task.ui.splash


import app.telda.task.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(repository: SplashRepository) : BaseViewModel(repository)