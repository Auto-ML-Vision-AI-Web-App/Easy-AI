import React from "react";
import axios from 'axios';

// components

import CardLineChart from "components/Cards/CardLineChart.js";
import CardBarChart from "components/Cards/CardBarChart.js";
import CardPageVisits from "components/Cards/CardPageVisits.js";
import CardSocialTraffic from "components/Cards/CardSocialTraffic.js";

{/*
export default function Dashboard() {
  return (
    <>
    <div className="flex flex-wrap">
        <div className="w-full px-4">
          <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded">
            <div className="relative w-full rounded h-600-px">
              <button type="button" onClick={null}/>
              <input type="file" name="file" onChange={null}/>
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-wrap">
        
        <div className="w-full xl:w-8/12 mb-12 xl:mb-0 px-4">
          <CardLineChart />
        </div>
        <div className="w-full xl:w-4/12 px-4">
          <CardBarChart />
        </div>
      </div>
      <div className="flex flex-wrap mt-4">
        <div className="w-full xl:w-8/12 mb-12 xl:mb-0 px-4">
          <CardPageVisits />
        </div>
        <div className="w-full xl:w-4/12 px-4">
          <CardSocialTraffic />
        </div>
      </div>
      </>
      );
    }
    
  */}


class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
           isSetImg: false
        };
    }

    imgUpload(e) {
        e.preventDefault();
        const api = axios.create({
            baseURL: 'http://localhost:8080'
        })

        var frm = new FormData();
        var photoFile = document.getElementById("file");
        frm.append("file", photoFile.files[0]);
        console.log(photoFile.files);

        api.post('/data', frm, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then(function (response) {
            alert("right")
            console.log(response);
        }).catch(function (error) {
            alert("error");
            console.log(error);
        });
    }

    clickImg(e) {
        e.preventDefault();
        document.getElementById('file').click();
    }
    
    watchingImg(e) {
        this.setState({
            isSetImg: true
        })
        var reader = new FileReader();
        
        reader.onload = function(e) {
            var img = document.createElement("img");
            img.setAttribute("src", e.target.result);
            img.setAttribute("width", "200");
            img.setAttribute("height", "200");
            document.querySelector("div#image_container").appendChild(img);
        };

        reader.readAsDataURL(e.target.files[0]);
    }

    selectImg() {
        if(this.state.isSetImg === false) {
            return(
                <img className ="imgsize" src="album.png" onClick={this.clickImg.bind(this)}></img>
            );
        }
    }

    render() {
        return(
          <div className="flex flex-wrap">
          <div className="w-full px-4">
          <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded">
            <form onSubmit={this.imgUpload.bind(this)}>
                <center>
                    <div id="header">
                        <h3>Choice Image</h3>
                    </div>
                    <div>
                        <input type='file' name='file' id='file' onChange={this.watchingImg.bind(this)} required></input>
                        {this.selectImg()}
                        <div id="image_container"></div>
                        <center>
                        <input type='submit' value="Submit"></input>
                        </center>
                    </div>
                </center>
            </form>
            </div>
            </div>
            </div>
        );
    }
  }

export default Dashboard