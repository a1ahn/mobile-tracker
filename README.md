# mobile-tracker

- 본인의 repository로 fork 하세요.
- 프로젝트를 생성하고 아래 제시된 기능들을 구현해보세요.
- 본인을 어필할 수 있는 용도로 마음껏 만들어보세요.
- 수시로 commit 하고 push 하세요.
- 최종 제출은 pull request(본인 github 계정명의 branch)로 대체합니다.
- 완성하지 못하더라도 해볼 수 있는 데 까지 해보세요.

## To-do

### Making simple tracker

- 아래의 references 에 제공된 링크들을 참조하세요.
- ICON JSON RPC 3.0을 이용하여 가장 마지막 10개의 블록을 가져오고 내림차순으로 화면에 보여주세요.
- 각 항목을 표시할 때 block_hash를 title로 사용해주세요.
- 항목을 선택할 경우 해당 항목의 confirmed_transaction_list를 보여주세요.

```
가급적이면 json 통신 구현에 있어서는 오픈소스 라이브러리를 최대한 배제해 보세요.
제시된 기능을 완성하지 못하더라도 자신이 할 수 있는 부분을 최대한 구현해보세요.
```

## 수행 완료

- 1. AsyncTask, HttpURLConnection을 활용한 통신
- 2. ICON JSON RPC 3.0을 이용하여 가장 마지막 10개의 블록을 가져오기 
- 3. 내림차순으로 리스트 출력 
- 4. 각 항목을 표시할 때 block_hash를 title로 사용


## References

- [ICON JSON RPC 3.0](https://github.com/icon-project/icon-rpc-server/blob/master/docs/icon-json-rpc-v3.md)
- [ICON Network](https://github.com/icon-project/icon-project.github.io/blob/master/docs/icon_network.md)
