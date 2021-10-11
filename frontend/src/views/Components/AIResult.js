import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import axios from 'axios';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';

import { Link } from "react-router-dom";


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
        height: 240,
    },
}));

export default function AIResult(props) {
    const [isSetAI, setAI] = useState();
    const [prevBtnDisabled, setPrevBtnDisabled] = useState(true)
    const [nextBtnDisabled, setNextBtnDisabled] = useState(true)

    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
    //const getParams = this.props.location.state.result_model;
    const downLoadAI = (e) => {
        axios({
            method: 'get',
            url: 'http://168.188.125.50:20017//ai-downloading',
            responseType: 'arraybuffer',
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
            .then(function (res) {
                //console.log(response.data);
                const disposition = res.attachment_filename;
                //const disposition = res.request.getResponseHeader('Content-Disposition')
                var fileName = "";
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1]) {
                    fileName = matches[1].replace(/['"]/g, '');
                }
                let blob = new Blob([res.data], { type: 'application/zip' })

                const downloadUrl = URL.createObjectURL(blob)
                let a = document.createElement("a");
                a.href = downloadUrl;
                //a.download = fileName;
                a.download = 'my-model.zip'
                document.body.appendChild(a);
                a.click();
            }).catch(function (error) {
                console.log(error);
            });
        /*const api = axios.create({
            baseURL: 'http://168.188.125.50:20017',
        })
        api.get('/ai-downloading').then(function (response) {
            console.log(response.data);
            history.push({
                pathname: '/admin/ai-checking',
                state: {
                    result_model: card_value,
                    result_message: response.data
                }
            })
        }).catch(function (error) {
            console.log(error);
        });*/
    }
    return (
        <>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                        <h2><strong>AI 결과 확인하기</strong></h2>
                        <div>
                            {props.location.state == undefined ?
                                <div>
                                    <h3>현재 AI를 생성하지 않았습니다.</h3>
                                    <h4>AI 만들기 눌러 AI를 생성해주세요.</h4>
                                </div>
                                :
                                <div>
                                    <h4>AI 종류 : {props.location.state.result_model}</h4>
                                    <Button onClick={downLoadAI} style={{ color: "gray" }}>AI 링크 : {props.location.state.result_message}</Button>
                                </div>
                            }</div>
                    </Paper>
                    <center>
                        <Button component={Link} to="/admin/ai-making"
                            disabled={prevBtnDisabled}
                            className={classes.stepButton}>
                            이전
                    </Button>
                        <Button component={Link} to="/admin/dashboard"
                            disabled={nextBtnDisabled}
                            className={classes.stepButton}>
                            다음
                    </Button>
                    </center>
                </Grid>
            </Grid>
        </>
    );
}