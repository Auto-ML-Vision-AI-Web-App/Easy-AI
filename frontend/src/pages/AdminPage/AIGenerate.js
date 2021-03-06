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
        pretrained_flag: false,
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
      const timeInfo = hour + "?????? " + min + "??? " + sec + "??? ????????? ????????? ?????? ???????????????.....";
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
        pretrained_flag: false,
        username: 'h01010',
        projectname: projectName,
        test_size: checked?testSize:-1,
        max_trials: checked?maxTrial:-1,
        epochs: checked?epochs:-1
      }
    }).then(function (response) {
      console.log(response.data.pretrained_info);
      props.setAIResult(response.data.history[0], response.data.pretrained_info);
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
            <h2><strong>AI ????????????</strong></h2>
            <div>
              {props.AIType == "" ?
                <div>
                  <p>?????? AI??? ???????????? ???????????????.<br />
                  AI ??????????????? ?????? ????????? AI??? ??????????????????.</p>
                </div>
                :
                <div>
                  <p>???????????? ?????? : {projectName}</p>
                  <p>????????? AI ?????? : {props.AIType}</p>
                  <hr></hr>
                  <br></br><br></br>

                  <Grid container alignItems="center" justifyContent="center" spacing={3}>
                    <Grid item xs={12}>
                      <ListItem style={{ background: '#EDEDED' }} button onClick={handleClick}>
                        <ListItemIcon>
                          <BuildIcon></BuildIcon>
                        </ListItemIcon>
                        <ListItemText primary="AI ?????? ????????????" />
                        {open ? <ExpandLess /> : <ExpandMore />}
                      </ListItem>
                      <Collapse in={open} timeout="auto" unmountOnExit>
                        <Paper elevation={3} style={{padding: '20px'}}>
                        <FormControlLabel
                        control={<Checkbox color="primary" onChange={handleChange} />}
                        label="??? ????????????"
                        />
                          <br></br><br></br>
                          <form onChange={handleFormChange}>
                            <InputAIMake checked={!checked} label="Test Size" defaultValue="0.2" helperText="????????? ?????? ?????????????????? ????????? ???????????? ????????? ???????????????. ????????? ???????????? AI??? ????????? ???????????? ?????? ???????????????."></InputAIMake>
                            <InputAIMake checked={!checked} label="Max Trial" defaultValue="1" helperText="????????? ???????????? AI??? ????????????. ????????? ??? ?????? ?????? ????????? ???????????????, ?????? ????????? AI??? ???????????? ???????????????."></InputAIMake>
                            <InputAIMake checked={!checked} label="Epochs" defaultValue="1" helperText="?????? ????????? ?????? AI??? ???????????? ????????? ???????????? ???????????????. ????????? ??? ?????? ?????? ????????? ???????????????, ????????? ????????? ??? ????????????."></InputAIMake>
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
                      <center><p style={{ fontSize: 20 }}>AI ????????????</p></center>
                    </Grid>
                  </Grid>
                  <br></br>

                  {!loadingStatus ?
                    <LinearProgress id="loadingProgress" style={{ display: 'none' }} />
                    : <Grid item xs={12}>
                      <center>
                        <LinearProgress id="loadingProgress" style={{ display: 'block' }} />
                        <p>??? ?????? ????????? ???????????? ????????????....</p>
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
