import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import HomeIcon from '@material-ui/icons/Home';
import PublishIcon from '@material-ui/icons/Publish';
import BarChartIcon from '@material-ui/icons/BarChart';
import AssignmentIcon from '@material-ui/icons/Assignment';
import BuildIcon from '@material-ui/icons/Build';
import TouchAppIcon from '@material-ui/icons/TouchApp';
//need to update

import { Link } from "react-router-dom";

export const homeListItems = (
  <div>
    <ListItem button component={Link} to="/">
      <ListItemIcon>
        <HomeIcon />
      </ListItemIcon>
      <ListItemText primary="Home" />
    </ListItem>
  </div>
);

export const mainListItems = (
  <div>
    <ListSubheader inset>Make AI</ListSubheader>
    <ListItem button component={Link} to="/admin/ai-choosing">
      <ListItemIcon>
        <TouchAppIcon />
      </ListItemIcon>
      <ListItemText primary="AI 선택하기" />
    </ListItem>
    <ListItem button component={Link} to="/admin/data-uploading">
      <ListItemIcon>
        <PublishIcon />
      </ListItemIcon>
      <ListItemText primary="데이터 업로드하기" />
    </ListItem>
    <ListItem button component={Link} to="/admin/ai-making">
      <ListItemIcon>
        <BuildIcon />
      </ListItemIcon>
      <ListItemText primary="AI 만들기" />
    </ListItem>
    <ListItem button component={Link} to="/admin/ai-checking">
      <ListItemIcon>
        <BarChartIcon />
      </ListItemIcon>
      <ListItemText primary="AI 결과 확인하기" />
    </ListItem>
  </div>
);

export const secondaryListItems = (
  <div>
    <ListSubheader inset>My Projects</ListSubheader>
    <ListItem button component={Link} to="/admin/projects">
      <ListItemIcon>
        <AssignmentIcon />
      </ListItemIcon>
      <ListItemText primary="프로젝트 관리하기" />
    </ListItem>
  </div>
);