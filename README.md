# tomato-clock
Android tomato clock app. Just for coursework but I like its cute appearance :)
(Also, have gotten a high mark.)

## Structure
* MainActivity
* TimerActivity
* HistoryActivity
* TextConfigNumberPicker
* Record (Entity)
* DAO
* Room
* Repository
* ViewModel

## Layout
### Main
Display the homepage with a RecyclerView.

### Timer
Start a new CountDownTimer for 5-90 min.  
Select time duration with custom NumberPicker (TextConfigNumberPicker.java).

### History
Display finished timers.  
All records are stored in Room database.

### Recyeler item
Show images and text in RecyclerView.
