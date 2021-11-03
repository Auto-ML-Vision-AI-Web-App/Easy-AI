import React, { useState, Component, Fragment } from 'react';
import { Link } from "react-router-dom";

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import axios from 'axios';

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
}));

export default function AIResult(props) {
    const [isSetAI, setAI] = useState();
    const [prevBtnDisabled, setPrevBtnDisabled] = useState(true)
    const [nextBtnDisabled, setNextBtnDisabled] = useState(true)

    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper);
    //const getParams = this.props.location.state.result_model;
    const downLoadAI = (e) => {
        axios({
            method: 'get',
            url: 'http://168.188.125.50:20017/ai-downloading',
            responseType: 'arraybuffer',
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
            .then(function (res) {
                //console.log(response.data);
                const disposition = res.attachment_filename;
                let blob = new Blob([res.data], { type: 'application/zip' })

                const downloadUrl = URL.createObjectURL(blob)
                let a = document.createElement("a");
                a.href = downloadUrl;
                a.download = 'my-model.zip'
                document.body.appendChild(a);
                a.click();
            }).catch(function (error) {
                console.log(error);
            });
    }
    return (
        <>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                        <h2><strong>AI 결과 확인하기</strong></h2>
                        <div>
                            {props.AIType === "" ?
                                <div>
                                    <h3>현재 AI를 생성하지 않았습니다.</h3>
                                    <h4>AI 만들기 눌러 AI를 생성해주세요.</h4>
                                </div>
                                :
                                <div>
                                    <h4>AI 종류 : {props.AIType}</h4>
                                    <CustomButton onClick={downLoadAI} color="info">AI 다운받기</CustomButton>
                                    <Link to={`/admin/ai-testing/${props.projectName}`} className={classes.link}>
                                        <CustomButton style={{ color: 'white', backgroundColor: '#6F3637' }}>생성된 모델로 테스트 하러 가기</CustomButton>
                                    </Link>
                                    <hr style={{ color: 'gray' }}></hr>
                                    <Grid container spacing={3}>
                                        <Grid item xs={6}>
                                            <AccuracyHighCharts historyJson={props.AIHistory} />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <LossHighCharts historyJson={props.AIHistory} />
                                        </Grid>
                                    </Grid>
                                </div>
                            }</div>
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
}

/*React Chart*/
class AccuracyHighCharts extends Component {

    render() {
        const jsonfile = this.props.historyJson;
        const options = {
            title: {
                text: 'Model history - Accuracy'
            },

            yAxis: {
                tickInterval: 0.01,
                title: {
                    text: ''
                }
            },

            xAxis: {
                title: {
                    text: 'epoch'
                }
            },

            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle'
            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                name: 'accuracy',
                data: jsonfile.accuracy
            }, {
                name: 'val_accuracy',
                data: jsonfile.val_accuracy
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
                <HighchartsReact highcharts={Highcharts} options={options} />
            </Fragment>
        );
    }
}

/*React Chart*/
class LossHighCharts extends Component {

    render() {
        const jsonfile = this.props.historyJson;
        const options = {
            title: {
                text: 'Model history - Loss'
            },

            yAxis: {
                tickInterval: 0.01,
                title: {
                    text: ''
                }
            },

            xAxis: {
                title: {
                    text: 'epoch'
                }
            },

            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle'
            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                name: 'loss',
                data: jsonfile.loss
            }, {
                name: 'val_loss',
                data: jsonfile.val_loss
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
                <HighchartsReact highcharts={Highcharts} options={options} />
            </Fragment>
        );
    }
}