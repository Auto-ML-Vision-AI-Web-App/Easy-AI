import React, { useState } from 'react';
import { Link, withRouter, useHistory } from "react-router-dom";
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';

import InputAIMake from 'components/CustomInput/CustomInputAIMake.js';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import LinearProgress from '@material-ui/core/LinearProgress';
import Slide from '@material-ui/core/Slide';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import BuildIcon from '@material-ui/icons/Build';

import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';

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
  const [testSize, setTestSize] = useState(0.2);
  const [maxTrial, setMaxTrial] = useState(1);
  const [epochs, setEpochs] = useState(1);
  const [checked, setChecked] = useState(false);

  const [open, setOpen] = React.useState(false);

  const handleClick = () => {
    setOpen(!open);
  };

  const handleFormChange = (e) => {
    const id = e.target.getAttribute('id');
    if (id == "Test Size") {
      setTestSize(e.target.value);
      console.log(e.target.value)
    }
    else if (id == "Max Trial") setMaxTrial(e.target.value);
    else if (id == "Epochs") setEpochs(e.target.value);
  }

  //here
  const aiTrainingInfo = (e) => {
    setLoadingStatus(true);
    console.log("request AI making time");
    const api = axios.create({
      baseURL: 'http://168.188.125.50:20017'
    })
    api.post('/ai-training/info', {
      params: {
        username: 'h01010',
        projectname: projectName,
        test_size: checked?testSize:-1,
        max_trials: checked?maxTrial:-1,
        epochs: checked?epochs:-1
      }
    }).then(function (response) {
      console.log(response.data);
      var seconds = response.data;
      var hour = parseInt(seconds / 3600);
      var min = parseInt((seconds % 3600) / 60);
      var sec = seconds % 60;
      const timeInfo = hour + "시간 " + min + "분 " + sec + "초 정도의 시간이 걸릴 예정입니다.....";
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
  const handleChange = (e) =>{
    setChecked(!checked);
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
                    <Grid item xs={12}>
                      <ListItem style={{ background: '#EDEDED' }} button onClick={handleClick}>
                        <ListItemIcon>
                          <BuildIcon></BuildIcon>
                        </ListItemIcon>
                        <ListItemText primary="AI 생성 세부설정" />
                        {open ? <ExpandLess /> : <ExpandMore />}
                      </ListItem>
                      <Collapse in={open} timeout="auto" unmountOnExit>
                        <Paper elevation={3} style={{padding: '20px'}}>
                        <FormControlLabel
                        control={<Checkbox color="primary" onChange={handleChange} />}
                        label="값 조정하기"
                        />
                          <br></br><br></br>
                          <form onChange={handleFormChange}>
                            <InputAIMake checked={!checked} label="Test Size" defaultValue="0.2" helperText="자신이 넣은 데이터셋에서 학습에 제외시킬 데이터 비율입니다. 제외된 데이터는 AI를 성능을 평가하는 데에 사용됩니다."></InputAIMake>
                            <InputAIMake checked={!checked} label="Max Trial" defaultValue="1" helperText="생성을 시도하는 AI의 수입니다. 숫자가 클 수록 생성 시간이 길어지지만, 선택 가능한 AI의 선택지가 늘어납니다."></InputAIMake>
                            <InputAIMake checked={!checked} label="Epochs" defaultValue="1" helperText="제일 성능이 좋은 AI가 데이터를 가지고 학습하는 횟수입니다. 숫자가 클 수록 생성 시간이 길어지지만, 성능은 올라갈 수 있습니다."></InputAIMake>
                          </form>
                        </Paper>

                      </Collapse>
                    </Grid>

                    <Grid item xs={12}>
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

    </>
  );
}

export default withRouter(AIGenerate);
