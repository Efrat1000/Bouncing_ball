# Bouncing_ball
Java Project- parallel and distributed
: 

מבוא:
מהו תכנות מקבילי ומבוזר?
התכנות המקבילי והמבוזר הם תחום בתוך התכנות המתמקד בכתיבת תוכניות אסינכרוניות שתהליכן מתבצע 
במקביל. בעוד התכנות הסינכרונית עובדת באופן ליניארי- שורה אחר שורה.
תכנות מקבילי ומבוזר מאפשרים לתהליכים שונים להתבצע בו זמנית, בדרך שתורמת לשיפור בביצועים וביכולת 
לעסוק בבעיות מורכבות ותהליכים שונים בו זמנית.
התכנות המקבילי מתבצע באמצעות תהליכים שונים שרצים באותו הזמן על מעבד מרובה ליבות או במערכת 
שמורכבת ממספר מחשבים עצמאיים. כל תהליך בתכנות המקבילי נקרא Thread,והוא יכול להתבצע באופן 
עצמאי ובלתי תלוי בתהליכים האחרים.
התכנות המבוזר, מצד שני, מתייחס לבניית יישומים שרצים על מספר מחשבים עצמאיים או תהליכים עצמאיים 
באותו מחשב. כל יחידה מבוזרת נקראת "תהליך מבוזר (Process Distributed" (ויכולה להתבצע באופן עצמאי 
במחשב שונה.
עקרונות התכנות המקבילי והמבוזר משתנים בהתאם לסוג הטכנולוגיה והפלטפורמה שמשתמשים בהם, קימים 
מספר עקרונות כלליים לתכנות מקבילי ומבוזר: שיתוף משאבים, תקשורת בין התהליכים, סנכרון בין התהליכים , 
ניהול שגיאות יעיל ועוד. 
הפרויקט שלי ומקבול מבוסס משימות-הסבר: 
מקבול מבוסס-משימות, )task parallelism )מאפשר רמת הפשטה גבוהה יותר בהשוואה למקבול 
מבוסס-תהליכונים. במודל מבוסס-משימות המתכנת מגדיר משימה, )task )שהיא יחידת עבודה 
עצמאית ובלתי תלויה ביתר המשימות, המקבול מבוסס משימות מאפשר לתוכנית לבצע עיבוד מקביל 
ובלתי תלוי על ידי תהליכים/תתי תהליכים, ובכך מגדיל את היעילות והביצועים של התוכנית. בנוסף, 
בשיטה זו ניתן לנהל את המשאבים ולהגביר את הסיכוי שלמערכת תגיבה מהירה לאירועים שונים.
הפרויקט שלי הוא דוגמה למקבול מבוסס משימות. בפרויקט זה, המשימות )בניית כדורים( מבוצעות 
באופן מקבולי באמצעות .ExecutorService ניתן ליצור כמה משימות חדשות בו זמנית ולשלוט במספר 
המשימות הפעילות והממתינות על ידי סינכרוניזציה מתאימה.
המתבונן בקוד המקור של תוכנית המבוססת על משימות לא יבחין בשינוי מהותי בהשוואה למקבול 
מבוסס-תהליכונים, משום שה"קסם" מתרחש מאחורי הקלעים בזמן הריצה. ומה שקורה, על קצה המזלג 
,הוא כדלקמן: עם תחילת הריצה חלק מהתהליכונים מייצר את המשימות, ומאחסן אותן במחסן משימות. 
יתר התהליכונים, בזמנם החופשי, ניגשים למחסן משימות ונוטלים משימות לביצוע. לאחר שכל 
המשימות שנוצרו גם בוצעו, התוכנית מסתיימת.
המשימה המרכזית בתוכנית היא לצייר כדורים תוך כדי תנועה שלהם בלוח המשחק. כל משימת כדור 
מיוצגת על ידי אובייקט מסוג Ball המממש את הממשק .Runnable כאשר יש גישה לשרשור הכדורים, 
מתבצע סנכרון באמצעות synchronized כדי למנוע חלוקת משאבים רשומים בשלבי הכניסה והיציאה 
מן המחסן.
בפרויקט הנ"ל – המשימות לא נוצרו בתחילת התוכנית – אלא ע"י המשתמש, וגם בסיומן – התוכנית לא 
מסתיימת כיון שהיא מאפשרת לעוד לחיצות-משימות להתבצע.

בנוסף, התוכנית מכילה תצורה גרפית של המשחק באמצעות ממשק ה GUI-של .Swing יש לחצן 
להשיגור של כדור חדש, ולתיאור הסטטוס של מספר הכדורים הפעילים והממתינים.
Service Executor- Service Executor הוא ממשק בספריית התכנות של ג'אווה 
)Java )שמאפשר לנהל ולבצע משימות מרובות באופן סינכרוני או אסינכרוני. הוא 
מספק חבילת שירותים עבור ניהול תהליכים ותזרימי עבודה בסביבת ה-Java.
Service Executor מספק מנגנון לביצוע תהליכים ברקע באמצעות פולימורפיזם של 
מופעי יישום הממשק Executor. הוא יכול לקבל משימות מסוג Runnable או 
Callable ולטפל בהן תוך שימוש בקבוצת תהליכים מובנית )pool thread )שמטפלת 
בשיבוצן של המשימות לתהליכים זמינים.
הגורמים שמקנים ל Service Executor להיות שימושיים הם:
.1 ניהול תהליכים: המנהל הפנימי )manager internal )של Service Executor יכול 
לנהל את מספר התהליכים הפעילים בו זמנית, להתמודד עם תהליכים מתעסקים 
ולנהל את זרימת העבודה.
.2 שיבוץ משימות: Service Executor מטפל בשיבוץ המשימות לתהליכים הפנויים 
ביותר מתוך הקבוצה של התהליכים המוגדרת.
.3 ניהול תור: במקרה שבו כמות המשימות גדולה מאוד ותהליכים פנויים כמעט כל 
הזמן, Service Executor יכול לנהל תור ולשיבץ משימות לתהליכים הפנויים כאשר 
הם זמינים.
בפרויקט הנוכחי, השתמשתי באובייקט ExecutorService בשילוב עם POOL של 
תהליכונים )Pool Thread )בגודל קבוע של 8 תהליכים )SIZE_POOL). POOL
התהליכונים מאפשר לך לשלוט על כמות התהליכים הפעילים בו ולספק לכל תהליך 
משימות לביצוע.
כאשר לוחצים על הכפתור "Launch "בממשק המשתמש, הפעולה launchAction
נקראת. בתוך פעולה זו, נוצר מופע חדש של הכיתה Ball ומוסיף אותו לרשימה של 
balls. לאחר מכן, באמצעות block synchronized, מגדירים כי התהליך נאלץ 
לחכות לתורו)waitCount++), ואז נעשה שימוש ב-ExecutorService לבצע את 
המופע החדש של הכיתה Ball בתוך תהליך זמין בפול המועדונים. כאשר התהליך 
מתחיל לרוץ, הוא מבצע את המשימה של הכיתה Ball בצורה פרודוקטיבית במיוחד.
ה-ExecutorService יכול לנהל ולשלוט על כמות התהליכים הפעילים בו ולנהל את 
הקריאה למשימות באופן מקבילי. בפרויקט הנוכחי, הוא מאפשר ניהול מספר כדורים 
באופן מקבילי, על ידי יצירה של מועדונים )threads )וביצוע משימות של הכיתה Ball
באמצעותם.
גישת Service מאפשרת ביצוע משימות ברקע וניהול התהליכים הקשורים אליהן 
בצורה יעילה וידידותית למשתמש.5
בעיות פיתוח 
בעיות שהתעוררו בעת פיתוח המערכת. 
.1אמינות. 
יש למנוע מצב של dataRace –בעת שכמה כדורים ישלחו לעדכון 
הסטטוס בו זמנית, ולכן השתמשתי ב- synchronized. 
.2ריבוי פעולות. 
הכפתורים פועלים בו זמנית וגם התצוגה והסטטוס – כולם הם 
runnable ועובדים במקביליות.
