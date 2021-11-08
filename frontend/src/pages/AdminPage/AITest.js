import React, { useState, Component, Fragment } from 'react';
import { Link } from "react-router-dom";

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import axios from 'axios';

import * as FileInput from "components/CustomInput/CustomFileInputCard.js";

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import CustomButton from "components/CustomButtons/Button.js";

import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";


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
    },
    fixedHeight: {
        height: 500,
    },
}));

export default function AIResult(props) {
    const [projectName, setProjectName] = useState("0b6pzo99ekh");
    const [dataset, setDataset] = useState([]);
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

    const addNewData = (_className, _path, _size) => {
        console.log("_setData in AITest")
        var data = new Object();
    
        data.className = _className;
        data.path = _path;
        data.size = _size;
        console.log(data);
        setDataset(dataset.concat(data));
      };

    return (
        <>
            <Grid container justifyContent="center" spacing={3}>
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
                                <h4>Project : {projectName}</h4>
                                <hr style={{ color: 'gray' }}></hr>
                                <Grid container justifyContent="center">
                                    <Grid item xs={12}>
                                    <FileInput.TestDataUpload projectName={projectName} dataClass="Test" id="testFile"
                                    setNewDate={addNewData}/>
                                    </Grid>
                                </Grid>
                            </div>
                        }
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
}