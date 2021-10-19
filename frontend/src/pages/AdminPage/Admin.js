import React from 'react';
import clsx from 'clsx';
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
import DataUpload from './DataUpload';
import DataCheck from './DataCheck';
import AIChoose from './AIChoose';
import AIResult from './AIResult';
import Projects from './Projects';
import AIGenerate from './AIGenerate';

import { Redirect, Switch, Route } from "react-router-dom";

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
    background: '#04ABC1'
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

function AccountCircleIcon(props) {
  return (
    <SvgIcon {...props}>
      <path d="M9 1C4.58 1 1 4.58 1 9s3.58 8 8 8 8-3.58 8-8-3.58-8-8-8zm0 2.75c1.24 0 2.25 1.01 2.25 2.25S10.24 8.25 9 8.25 6.75 7.24 6.75 6 7.76 3.75 9 3.75zM9 14.5c-1.86 0-3.49-.92-4.49-2.33C4.62 10.72 7.53 10 9 10c1.47 0 4.38.72 4.49 2.17-1 1.41-2.63 2.33-4.49 2.33z" />
    </SvgIcon>
  );
}

export default function Admin() {
  const classes = useStyles();
  const [open, setOpen] = React.useState(true);
  const [AIType, setAIType_Admin] = React.useState("");
  const [projectName, setProjectName_Admin] = React.useState("");
  const [AIHistory, setAIHistory_Admin] = React.useState();
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  const setProjectName = (_projectName) =>{
    console.log("Admin projectName : "+ _projectName)
    setProjectName_Admin(_projectName);
  }

  const setAIHistory = (_history) => { 
    setAIHistory_Admin(_history);
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
          <IconButton>
              <AccountCircleIcon style={{ color: 'white', fontSize: 40 }}/>
          </IconButton>
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
            ></DataUpload>} />
          <Route path="/admin/data-checking" exact component={DataCheck} />
          <Route path="/admin/ai-making" exact 
            component={()=>
            <AIGenerate
              AIType={AIType}
              setAIHistory={setAIHistory}
            ></AIGenerate>} />
          <Route path="/admin/ai-checking" exact component={()=>
            <AIResult
              AIType={AIType}
              AIHistory={AIHistory}
            ></AIResult>} />

          <Route path="/admin/projects" exact component={Projects} />
          <Redirect from="/admin" to="/admin/ai-choosing" />
        </Switch>
        <Box pt={4}>
            <Copyright />
        </Box>
        </Container>
      </main>
    </div>
  );
}