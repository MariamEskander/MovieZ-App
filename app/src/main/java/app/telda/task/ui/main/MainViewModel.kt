package app.telda.task.ui.main


import app.telda.task.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(repository: MainRepository) : BaseViewModel(repository)