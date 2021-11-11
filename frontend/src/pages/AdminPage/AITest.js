import React, { useState, Component, Fragment } from 'react';
import { Link } from "react-router-dom";

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import axios from 'axios';

import * as FileInput from "components/CustomInput/CustomFileInputCard.js";

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import LinearProgress from '@material-ui/core/LinearProgress';
import CustomButton from "components/CustomButtons/Button.js";
import IconButton from '@material-ui/core/IconButton';
import PlayCircleFilledIcon from '@material-ui/icons/PlayCircleFilled';
import { setCookie, getCookie, removeCookie } from 'components/Cookie.js';

import CustomListDown from "components/CustomDropdown/CustomListDown.js";



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
    button: {
        color: "#6F3637",
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
    },
    fixedHeight: {
        minHeight: 500,
    },
}));

export default function AIResult(props) {
    const [projectName, setProjectName] = useState(localStorage.getItem("projectName") == undefined ? "" : localStorage.getItem("projectName"));
    const [downloadFlag, setDownloadFlag] = useState(false);
    const [loadingStatus, setLoadingStatus] = React.useState(false);
    const [testResult, setTestResult] = useState([]);
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

    const addNewData = (_className, _dataSet, _dataSize) => {
        console.log("dataUpload in AITest")
      };

    const addResultData = (_className, _dataSet, _dataSize) => {
        console.log("_setData in DataUpload")
        var data = new Object();
    
        data.className = _className;
        data.dataSet = _dataSet;
        data.dataSize = _dataSize;
        setTestResult(testResult.concat(data));
        console.log(data);
      };

    const startTest = (e) => {
        setLoadingStatus(true);
        console.log("starting ai testing")
        const aiAPI = axios.create({
            baseURL: 'http://168.188.125.50:20017'
        })
        aiAPI.post('/ai-testing', {
            params: {
                username: 'h01010',
                projectname: projectName,
            }
        }).then(function (response) {
            console.log(response.data);
            var result = response.data.result;
            setLoadingStatus(false);
            setDownloadFlag(true);
            result.map((resultList, listIdx) => {
                console.log(resultList);
                addResultData(resultList.class, resultList.classifiedResult, resultList.classifiedResult.length);
            });
        }).catch(function (error) {
            console.log(error);
        });
    }

    const downLoadTestResult = (e) => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/data',
            params: { projectName: projectName, category: "test" },
            responseType: 'arraybuffer',
            headers: {
                'Authorization': "Bearer " + getCookie('access-token'),
                'Content-Type': 'multipart/form-data',
                'Accept': 'application/zip'
            }
        })
            .then(function (res) {
                //console.log(response.data);
                const disposition = res.attachment_filename;
                let blob = new Blob([res.data], { type: 'application/zip' })

                const downloadUrl = URL.createObjectURL(blob)
                let a = document.createElement("a");
                a.href = downloadUrl;
                a.download = 'test-result.zip'
                document.body.appendChild(a);
                a.click();
            }).catch(function (error) {
                console.log(error);
            });
    }

    return (
        <>
            <Grid container spacing={1} justifyContent="center" alignItems="center">
                <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                        <h2><strong>AI 테스트하기</strong></h2>
                        {projectName === "" ?
                            <div>
                                <h3>현재 AI를 생성하지 않았습니다.</h3>
                                <h4>AI 만들기 눌러 AI를 생성해주세요.</h4>
                                <div>
                                    <h4>Project : 프로젝트 이름{projectName}</h4>
                                    <hr style={{ color: 'gray' }}></hr>
                                    {/*exception 처리 필요*/}
                                </div>
                            </div> :
                            <div>
                                <p>프로젝트 이름 : {projectName}</p>
                                <hr style={{ color: 'gray' }}></hr>
                                <br></br>
                                <p>생성된 AI를 테스트하기 위해 분류하고싶은 이미지를 업로드해주세요</p>
                                <br></br>
                                <br></br>
                                <Grid container justifyContent="center">
                                    <Grid item xs={6}>
                                        <FileInput.TestDataUpload projectName={projectName} dataClass="Test" id="testFile"
                                            setNewDate={addNewData} />
                                    </Grid>
                                    <Grid item xs={4}>
                                        <center>
                                            <IconButton aria-label="ai test"
                                                onClick={startTest} className={classes.button}>
                                                <PlayCircleFilledIcon style={{ fontSize: 100 }} />
                                            </IconButton>
                                        </center>
                                        <center><p style={{ fontSize: 20 }}>AI 테스트하기/사용하기</p></center>
                                    </Grid>

                                    {!loadingStatus ?
                                    <LinearProgress id="loadingProgress" style={{ display: 'none' }} />
                                    : <Grid item xs={12}>
                                    <center>
                                        <LinearProgress id="loadingProgress" style={{ display: 'block' }} />
                                        <p>테스트를 하여 이미지를 분류하고 있습니다....</p>
                                    </center>
                                        </Grid>}
                                </Grid>

                                <br></br>
                                <hr style={{ color: 'gray' }}></hr>

                                {downloadFlag?
                                <Grid container justifyContent="center" spacing={2}>

                                    <Grid item xs={12}>
                                        <center>
                                            <Button style={{ color: 'white', backgroundColor: '#6F3637' }}
                                                onClick={downLoadTestResult}>이미지 분류 결과 다운로드하기</Button>
                                        </center>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <center><CustomListDown dataset={testResult}></CustomListDown></center>
                                    </Grid>
                                </Grid>:<></>}
                            </div>
                        }
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
}