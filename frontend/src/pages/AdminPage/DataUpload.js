import React, { useState, Component, Fragment } from 'react';
import axios from 'axios';
import { Switch, Route, Link, withRouter, useHistory } from "react-router-dom";
import { setCookie, getCookie, removeCookie } from 'components/Cookie.js';
import { refreshToken } from 'components/Token.js';
import * as FileInput from "components/CustomInput/CustomFileInputCard.js";

import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from "components/CustomButtons/Button.js";

import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import clsx from 'clsx';

import Highcharts from "highcharts";
import PieChart from "highcharts-react-official";

import '../../assets/css/dataupload.css'

const useStyles = makeStyles((theme) => ({
  stepButton: {
    border: "red",
    backgroundColor: "#eee6c4",
    color: "black",
    fontSize: 30,
    margin: '10px',
    "&:hover,&:focus": {
      backgroundColor: "#333333",
      color: "#fff",
      boxShadow:
        "0 14px 26px -12px rgba(51, 51, 51, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(51, 51, 51, 0.2)",
    },
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
    minHeight: 300,
  },
}));

export default function DataUpload(props) {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper);
  const [projectName, setProjectName] = useState(localStorage.getItem("projectName") == undefined ? "" : localStorage.getItem("projectName"));
  const [class1Name, setClass1Name] = useState("");
  const [class2Name, setClass2Name] = useState("");
  const [dataset, setDataset] = useState([]);
  const [prevBtnDisabled, setPrevBtnDisabled] = useState(true);
  const [nextBtnDisabled, setNextBtnDisabled] = useState(true);
  const [projectId, setProjectId] = useState(null);

  const changeClassName = (_name, _className) => {
    console.log("changeClassName in DataUpload")
    if (_name === "Class 1") setClass1Name(_className);
    if (_name === "Class 2") setClass2Name(_className);
  };

  const addNewData = (_className, _path, _size) => {
    console.log("_setData in DataUpload")
    var data = new Object();

    data.className = _className;
    data.path = _path;
    data.size = _size;
    console.log(data);
    setDataset(dataset.concat(data));
  };

  return (
    <>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Paper className={fixedHeightPaper}>
            <div>
              <h2><strong>데이터 업로드하기</strong></h2>
              {props.AIType === "" ?
                <div>
                  <p>현재 AI가 선택되지 않았습니다.<br />
                  메뉴에서 [AI 선택하기]를 눌러 데이터를 입력해주세요.</p>
                </div>
                :
                <div>
                  <h4><strong>생성 AI 종류 : {props.AIType}</strong></h4>

                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                      <p>각 버튼을 눌러, 해당 데이터를 업로드해주세요.</p>
                    </Grid>

                    <Grid item xs={6}>
                      <FileInput.TrainDataUpload projectName={projectName} dataClass="Class 1" id="class1File"
                        onChange={changeClassName} setNewDate={addNewData} />
                    </Grid>
                    <Grid item xs={6}>
                      <FileInput.TrainDataUpload projectName={projectName} dataClass="Class 2" id="class2File"
                        onChange={changeClassName} setNewDate={addNewData} />
                    </Grid>

                    {/*<Grid item xs={12}>

                <Button
                  variant="contained"
                  color="info"
                  className={classes.button}
                  startIcon={<CloudUploadIcon />}
                >Upload인데, 지금은 일단 작동x</Button>
            </Grid>*/}
                    <Button onClick={refreshToken}>REFRESH TOKEN</Button>
                  </Grid>

                  <hr style={{ background: 'gray' }}></hr>

                  {dataset.length != 2 ?
                    <></> :
                    <Grid container justifyContent="center" spacing={2}>
                      <Grid item xs={12}>
                        <DataResultChart dataset={dataset}></DataResultChart>
                      </Grid>
                      <Grid item xs={12}>
                        <center>
                          <Link to="/admin/ai-making/" className={classes.link}>
                            <Button style={{ color: 'white', backgroundColor: '#6F3637' }}>생성된 모델로 테스트 하러 가기</Button>
                          </Link>
                        </center>
                      </Grid>
                    </Grid>

                  }
                </div>
              }
            </div>
          </Paper>
        </Grid>
      </Grid>
    </>
  );
}


/*React Chart*/
class DataResultChart extends Component {

  render() {
    const jsonfile = this.props.dataset;
    let total = 0;
    jsonfile.map((data, idx) => (
      total = total + data.size
    ));

    const options = {
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
      },
      title: {
        text: '데이터 업로드 결과'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.size}개</b>'
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b> : {point.percentage:.1f} %'
          }
        }
      },

      series: [{
        name: '데이터 수',
        colorByPoint: true,
        data: [{
          name: jsonfile[0].className,
          size: jsonfile[0].size,
          y: jsonfile[0].size / total * 100,
          color: '#08AAC1',
          sliced: true,
          selected: true
        }, {
          name: jsonfile[1].className,
          size: jsonfile[1].size,
          y: jsonfile[1].size / total * 100,
          color: '#5C5C5C'
        }]
      }],

      responsive: {
        rules: [{
          condition: {
            maxWidth: 300
          },
          chartOptions: {
            legend: {
              layout: 'horizontal',
              align: 'center',
              verticalAlign: 'bottom'
            }
          }
        }]
      }

    }
    return (
      <Fragment>
        <PieChart highcharts={Highcharts} options={options} />
      </Fragment>
    );
  }
}