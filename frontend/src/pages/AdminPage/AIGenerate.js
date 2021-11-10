import React, { useState } from 'react';
import { Link, withRouter, useHistory } from "react-router-dom";
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';

import InputAIMake from 'components/CustomInput/CustomInputAIMake.js';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import LinearProgress from '@material-ui/core/LinearProgress';
import Button from "components/CustomButtons/Button.js";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';

import IconButton from '@material-ui/core/IconButton';
import PlayCircleFilledIcon from '@material-ui/icons/PlayCircleFilled';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const drawerWidth = 240;

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
  root: {
    display: 'flex',
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
    minHeight: 700,
  },
}));

function AIGenerate(props) {
  const history = useHistory();
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const [projectName, setProjectName] = useState(localStorage.getItem("projectName") == undefined ? "" : localStorage.getItem("projectName"));
  const [loadingStatus, setLoadingStatus] = React.useState(false);
  const [traingTime, setTrainingTime] = useState("");
  const [open, setOpen] = React.useState(false);
  const [testSize, setTestSize] = useState(0.2);
  const [maxTrial, setMaxTrial] = useState(1);
  const [epochs, setEpochs] = useState(1);

  const handleFormChange = (e) => {
    const id = e.target.getAttribute('id');
    if (id == "Test Size") {
      setTestSize(e.target.value);
      console.log(e.target.value)
    }
    else if (id == "Max Trial") setMaxTrial(e.target.value);
    else if (id == "Epochs") setEpochs(e.target.value);
  }
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  //here
  const aiTrainingInfo = (e) =>{
    setLoadingStatus(true);
    console.log("request AI making time");
    const api = axios.create({
      baseURL: 'http://168.188.125.50:20017'
    })
    api.post('/ai-training/info', {
      params: {
        username: 'h01010',
        projectname: projectName,
        test_size: testSize,
        max_trials: maxTrial,
        epochs: epochs
      }
    }).then(function (response) {
      console.log(response.data);
      var seconds = response.data;
      var hour = parseInt(seconds/3600);
      var min = parseInt((seconds%3600)/60);
      var sec = seconds%60;
      const timeInfo = hour+"시간 "+min+"분 " + sec+"초 정도의 시간이 걸릴 예정입니다.....";
      setTrainingTime(timeInfo)
      aiTrainingMaking();
    }).catch(function (error) {
      console.log(error);
    });
  }
  const aiTrainingMaking = (e) => {
    console.log("request AI training");
    const api = axios.create({
      baseURL: 'http://168.188.125.50:20017'
    })
    api.post('/ai-training/making', {
      params: {
        username: 'h01010',
        projectname: projectName,
        test_size: testSize,
        max_trials: maxTrial,
        epochs: epochs
      }
    }).then(function (response) {
      props.setAIHistory(response.data);
      setLoadingStatus(false);
      history.push({
        pathname: '/admin/ai-checking',
      })
    }).catch(function (error) {
      console.log(error);
    });
  }
  return (
    <>
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Paper className={fixedHeightPaper}>
            <h2><strong>AI 생성하기</strong></h2>
            <div>
              {props.AIType == "" ?
                <div>
                  <p>현재 AI가 선택되지 않았습니다.<br />
                  AI 선택하기를 눌러 생성할 AI를 선택해주세요.</p>
                </div>
                :
                <div>
                  <p>프로젝트 이름 : {projectName}</p>
                  <p>생성할 AI 종류 : {props.AIType}</p>
                  <hr></hr>
                  <br></br><br></br>

                  <Grid container alignItems="center" justifyContent="center" spacing={3}>
                    <Grid item xs={8}>
                      <Paper elevation={3}>
                        <form onChange={handleFormChange}>
                          <InputAIMake label="Test Size" defaultValue="0.2" helperText="자신이 넣은 데이터셋에서 학습에 제외시킬 데이터 비율입니다. 제외된 데이터는 AI를 성능을 평가하는 데에 사용됩니다."></InputAIMake>
                          <InputAIMake label="Max Trial" defaultValue="1" helperText="생성을 시도하는 AI의 수입니다. 숫자가 클 수록 생성 시간이 길어지지만, 선택 가능한 AI의 선택지가 늘어납니다."></InputAIMake>
                          <InputAIMake label="Epochs" defaultValue="1" helperText="제일 성능이 좋은 AI가 데이터를 가지고 학습하는 횟수입니다. 숫자가 클 수록 생성 시간이 길어지지만, 성능은 올라갈 수 있습니다."></InputAIMake>
                        </form>
                      </Paper>
                    </Grid>

                    <Grid item xs={4}>
                    <center>
                      <IconButton aria-label="ai make"
                        onClick={aiTrainingInfo} className={classes.button}>
                        <PlayCircleFilledIcon style={{ fontSize: 130 }} />
                      </IconButton>
                      </center>
                      <center><p style={{ fontSize: 20 }}>AI 생성하기</p></center>
                    </Grid>
                  </Grid>
                  <br></br>
                  
                  {!loadingStatus ?
                    <LinearProgress id="loadingProgress" style={{ display: 'none' }} />
                    : <Grid item xs={12}>
                      <center>
                        <LinearProgress id="loadingProgress" style={{ display: 'block' }} />
                        <p>총 학습 시간을 예상하고 있습니다....</p>
                        <p>{traingTime}</p>
                      </center>
                      </Grid>}
                </div>
              }</div>
          </Paper>
        </Grid>
      </Grid>

      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle id="alert-dialog-slide-title">{"정말 AI를 생성하시겠습니까?"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-slide-description">
            업로드한 데이터, 입력한 클래스를 기반으로 AI 생성과정을 시작할 것입니다.<br></br>
            시작하기 전에 다시 한번 'AI 생성 관련 정보들'을 확인해주세요.
            //시간 걸릴거라고 추가
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => { aiMaking(props.AIType) }} color="primary">
            생성하기
          </Button>
          <Button onClick={handleClose} color="secondary">
            취소
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default withRouter(AIGenerate);
