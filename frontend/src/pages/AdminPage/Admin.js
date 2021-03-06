import React from 'react';
import clsx from 'clsx';
import '../../styles/css/index.css'
import {setCookie, getCookie, removeCookie} from 'components/Cookie.js';

import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import Box from '@material-ui/core/Box';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import Container from '@material-ui/core/Container';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import SvgIcon from '@material-ui/core/SvgIcon';

import { homeListItems, mainListItems, secondaryListItems } from './listItems';
import AIChoose from './AIChoose';
import DataUpload from './DataUpload';
import DataCheck from './DataCheck';
import AIResult from './AIResult';
import Projects from './Projects';
import AIGenerate from './AIGenerate';
import AITest from './AITest';

import '../../styles/css/Admin.css'

import { Redirect, Switch, Route } from "react-router-dom";
import { color } from 'highcharts';

function Copyright() {
  return (
    <Typography variant="body2" color="textPrimary" align="center">
      {'Copyright © '}
      <a style={{color:"gray"}} href="seoyeonju1198@gmail.com/" >
        h01010
      </a>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    //backgroundImage: 'radial-gradient(circle, #49cee0, #81d9e3, #aae3e8, #cfedee, #f1f6f6)',
    //backgroundImage: 'linear-gradient(to bottom, #08aac1, #57bbc0, #84cbc3, #abdbca, #cfead8, #cfead8, #cfead8, #cfead8, #abdbca, #84cbc3, #57bbc0, #08aac1)'
    //backgroundImage: 'linear-gradient(to right bottom, #08aac1, #57bbc0, #84cbc3, #abdbca, #cfead8, #cfead8, #cfead8, #cfead8, #abdbca, #84cbc3, #57bbc0, #08aac1)',
    backgroundImage: 'radial-gradient(circle, #68d9e9, #92e1ed, #b5e9f1, #d4f1f6, #f2f9fa)',
  },
  footer: {
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    background: '#007688'
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
    background: '#007688',
    color: 'white',
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
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

export default function Admin() {
  const classes = useStyles();
  const [redirectPath, setRedirectPath] = React.useState(getCookie('access-token')!=undefined? '/admin/ai-choosing':'/login-page')
  const [open, setOpen] = React.useState(true);
  const [AIType, setAIType_Admin] = React.useState("");
  const [projectName, setProjectName_Admin] = React.useState("");
  const [AIHistory, setAIHistory_Admin] = React.useState();
  const [AIPretrainedInfo, setPretrainedInfo_Admin] = React.useState();
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  const setProjectName = (_projectName) =>{
    localStorage.setItem('projectName', _projectName)
    setProjectName_Admin(_projectName);
  }

  const setAIResult = (_history, _pretrained_infoained) => { 
    setAIHistory_Admin(_history);
    setPretrainedInfo_Admin(_pretrained_infoained);
  }

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
        <Toolbar className={classes.toolbar}>
          <IconButton
            edge="start"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
          >
            <MenuIcon />
          </IconButton>
          <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        classes={{
          paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
        }}
        open={open}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>{homeListItems}</List>
        <Divider />
        <List>{mainListItems}</List>
        <Divider />
        <List>{secondaryListItems}</List>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Container maxWidth="lg" className={classes.container}>
        <Switch>
          <Route path="/admin/ai-choosing" exact
            component={()=>
            <AIChoose
              setAIType={function (_type) {
                setAIType_Admin(_type)
              }}
              setProjectName={setProjectName}
            ></AIChoose>} />
          
          <Route path="/admin/data-uploading" exact component={()=>
            <DataUpload
              AIType={AIType}
              projectName={projectName}
            ></DataUpload>}
          />

          <Route path="/admin/data-checking" exact component={()=>
            <DataCheck
            ></DataCheck>}
          />
          
          <Route path="/admin/ai-making" exact 
            component={()=>
            <AIGenerate
              AIType={AIType}
              setAIResult={setAIResult}
            ></AIGenerate>}
          />
          
          <Route path="/admin/ai-checking" exact component={()=>
            <AIResult
              projectName={projectName}
              AIType={AIType}
              AIPretrainedInfo={AIPretrainedInfo}
              AIHistory={AIHistory}
            ></AIResult>}
          />
          
          <Route path="/admin/ai-testing" exact component={()=>
            <AITest
              AIType={AIType}
              AIHistory={AIHistory}
            ></AITest>}
          />

          <Route path="/admin/projects" exact component={Projects} />
          <Redirect from="/admin" to={redirectPath} />
        </Switch>

        <center>
        <Box className={classes.footer} pt={4}>
            <Copyright />
        </Box>
        </center>
        </Container>
      </main>
    </div>
  );
}