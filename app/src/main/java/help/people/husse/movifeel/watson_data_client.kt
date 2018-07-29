package help.people.husse.movifeel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*

class watson_data_client : AppCompatActivity() {
    // Must wrap the request in a async because shouldnt run network stuff
    // on the main ui thread

    fun create_request(x: String): AnalysisResults {

        val analyzer = NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "4eb1a2eb-c85f-4bcc-9abf-3e0886c3840b",
                "ZWDsvG6UqjQZ"
        )
        // extract all entities
        val entityOptions = EntitiesOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .build()
        // getting the sentitments
// may be where the documents are true
        val sentimentOptions = SentimentOptions.Builder()
                .document(true)
                .build()
        // sentiment and entities are combined to the features object
// which is updated into the analyze object and sent to ibm watson
// also making the keyword options\
        val KeywordsOptions = com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions.Builder().emotion(true).sentiment(true).limit(2).build();
        val features = Features.Builder()
                .entities(entityOptions)
                .sentiment(sentimentOptions)
                .keywords(KeywordsOptions)
                .build()
        // creating the analyzer object
        val analyzerOptions = AnalyzeOptions.Builder()
                .text(x)
                .features(features)
                .build()
// running it on the same thread
// val is constant



try {


    var results = analyzer.analyze(analyzerOptions).execute()
    return results;

}catch(e: Exception)
{



}


// caught the error now need to find a way for it to be handled



// it will sometimes trigger here so we must put a warning
 return analyzer.analyze(analyzerOptions).execute()
    }

    fun get_emotions(results: AnalysisResults):Array<String> {

        // making a counter also
        var counter = 0;
        var counter_help =0;



        // since watson returns the emotions in an object we can
        // need to compare all the numbers
        // first we are going to have a counter to intialize our arrays
        for (help in results.keywords)
        {
            counter_help++
        }
        var holder= Array<String>(counter_help,{"Nothing"})
        for (entity in results.keywords)  {
            // creating the array to grab the max value to put in the string array
            val validEmotions = arrayOf("Anger", "Joy", "Disgust",
                    "Fear", "Sadness")
            val emotionValues = arrayOf(
                    entity.emotion.anger,
                    entity.emotion.joy,
                    entity.emotion.disgust,
                    entity.emotion.fear,
                    entity.emotion.sadness
            )
            // getting the max to get the emotion
            val currentEmotion = validEmotions[emotionValues.indexOf(emotionValues.max())]
             holder[counter]=currentEmotion;
            counter=counter+1;


        }
        return holder;
    }

    // functions for grabbing the key words
    fun get_keywords(results: AnalysisResults): Array<String>{
        // making a counter also
        var counter = 0;
        // holding the keywords
        var counter_help=0

        // since watson returns the emotions in an object we can
        // need to compare all the numbers
        for (help in results.keywords)
        {
            counter_help++
        }
        var holderx= Array<String>(counter_help,{"Nothing"})
        for (entity in results.keywords) {
            // creating the array to grab the max value to put in the string array
            holderx[counter]=entity.text

                counter ++


        }
        return holderx;

    }
}

