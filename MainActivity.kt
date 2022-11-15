package com.example.controladorsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    var boton : Button?= null
    var btbtotn : ImageButton?= null
    private var progressBar: ProgressBar? = null
    private var progressBar2: ProgressBar? = null
    private var i = 0
    private var k = 0
    private var count = 0
    private var txtView: TextView? = null
    private var txtView2: TextView? = null
    private var txtViewPeriodo: TextView? = null
    private var txtViewTiempoTotal: TextView? = null
    private var txtrepeticiones: TextView? = null
    private val handler = Handler()

    fun setParameters(tp: String, tt: String, rep: String): IntArray {
        var arr = IntArray(2) // [0] -> valor del peiodo [1]-> numero de repeticiones

        if (!tp.isNullOrEmpty() && !tt.isNullOrEmpty() && !rep.isNullOrEmpty() ) {
            arr[0] = 0
            arr[1] = 0
            Toast.makeText(this,"No editar mas de 2 valores", Toast.LENGTH_LONG).show()
        }else if (tp.isNullOrEmpty() && !tt.isNullOrEmpty() && !rep.isNullOrEmpty() ) {
            arr[0] = (((tt.toInt()).toDouble() / rep.toInt()).toInt())*1000
            arr[1] = rep.toInt()
        }else if (!tp.isNullOrEmpty() && tt.isNullOrEmpty() && !rep.isNullOrEmpty()){
            arr[0] = tp.toInt()*1000
            arr[1] = rep.toInt()
        }else if (!tp.isNullOrEmpty() && !tt.isNullOrEmpty() && rep.isNullOrEmpty()) {
            arr[0] = tp.toInt()*1000
            arr[1] = ((tt.toInt()).toDouble() / tp.toInt()).toInt()
        }
        else{
            arr[0] = 0
            arr[1] = 0
            Toast.makeText(this,"Coloque valores correctos", Toast.LENGTH_LONG).show()
        }
        return arr
    }
    fun gotoativity(){
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton = findViewById<Button>(R.id.button1)
        btbtotn = findViewById<ImageButton>(R.id.buttonbt)
        txtView = findViewById<TextView>(R.id.text_view)
        progressBar = findViewById<ProgressBar>(R.id.progressCompleteProcess) as ProgressBar
        txtView2 = findViewById<TextView>(R.id.text_view2)
        progressBar2 = findViewById<ProgressBar>(R.id.progressCompleteProcess2) as ProgressBar
        txtViewPeriodo = findViewById<TextView>(R.id.editTextPeriodo)
        txtViewTiempoTotal = findViewById<TextView>(R.id.editTextTiempoTotal)
        txtrepeticiones = findViewById<TextView>(R.id.editTextNumRepeticiones)

        boton!!.setOnClickListener {

            Toast.makeText(this,"Proceso Iniciado",Toast.LENGTH_LONG).show()
            i = 0
            progressBar!!.visibility = View.VISIBLE
            progressBar2!!.visibility = View.VISIBLE
            Thread(Runnable {
                val strPeriodo: String = txtViewPeriodo!!.text.toString()
                val strTiempoTotal: String = txtViewTiempoTotal!!.text.toString()
                val strrepeticiones: String = txtrepeticiones!!.text.toString()
                val parameters = setParameters(strPeriodo,strTiempoTotal,strrepeticiones) // [0] -> valor del peiodo [1]-> numero de repeticiones

                println("El valor es $parameters")
                // this loop will run until the value of i becomes 99
                try{
                    count = 1
                    while (i < 100) {

                        k = 0
                        //val exp = (log10(parameters[1].toDouble()) + 1).toInt()
                        //i += (Math.pow(10.toDouble() ,exp.toDouble())/parameters[1]).toInt()
                        i += (100.0 / (parameters[1]) ).toInt()+1
                        println("El valor es $i")
                        // Update the progress bar and display the current value
                        handler.post(Runnable {
                            progressBar2!!.progress = i
                            // setting current progress to the textview
                            txtView2!!.text = "Repeticion #"+ count.toString()

                        })
                        try {
                            Thread.sleep(20)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        count+=1
                        while(k < 100){
                            k += 20
                            println("El valor es $k")
                            handler.post(Runnable {
                                progressBar!!.progress = k
                                // setting current progress to the textview
                                txtView!!.text = k.toString() + "/" + progressBar!!.max
                            })

                            try {
                                Thread.sleep((parameters[0].toDouble()/5).toLong())
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }

                    }
                }catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                // setting the visibility of the progressbar to invisible
                // or you can use View.GONE instead of invisible
                // View.GONE will remove the progressbar
                progressBar!!.visibility = View.INVISIBLE
                txtView2!!.text = ""
                progressBar2!!.visibility = View.INVISIBLE
                txtView!!.text = ""
                txtViewPeriodo!!.text = ""
                txtViewTiempoTotal!!.text = ""
                txtrepeticiones!!.text = ""
            }).start()
        }

        btbtotn!!.setOnClickListener {
            gotoativity()
        }



    }
}