# tomato-clock
Android tomato clock app. Just for coursework but I like the images.

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

### Histoty
Display finished timers.  
All records are stored in Room database.

### Recyeler_item
Show images and text in RecyclerView.
