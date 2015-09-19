%% Import data from text file.

%% Initialize variables.
filename = 'results.txt';
delimiter = ',';
startRow = [4,7,12,15,20,23,28,31,36,39,44,47,52,55,60,63,68,71,76,79,84,87,92,95,100,103,108,111,116,119,124,127,132,135,140,143,148,151,156,159,164,167,172,175,180,183,188,191];
endRow = [4,7,12,15,20,23,28,31,36,39,44,47,52,55,60,63,68,71,76,79,84,87,92,95,100,103,108,111,116,119,124,127,132,135,140,143,148,151,156,159,164,167,172,175,180,183,188,191];

%% Format string for each line of text:
formatSpec = '%s%*s%*s%*s%*s%*s%*s%*s%[^\n\r]';

%% Open the text file.
fileID = fopen(filename,'r');

%% Read columns of data according to format string.
dataArray = textscan(fileID, formatSpec, endRow(1)-startRow(1)+1, 'Delimiter', delimiter, 'HeaderLines', startRow(1)-1, 'ReturnOnError', false);
for block=2:length(startRow)
    frewind(fileID);
    dataArrayBlock = textscan(fileID, formatSpec, endRow(block)-startRow(block)+1, 'Delimiter', delimiter, 'HeaderLines', startRow(block)-1, 'ReturnOnError', false);
    dataArray{1} = [dataArray{1};dataArrayBlock{1}];
end

%% Close the text file.
fclose(fileID);

%% Create output variable
results = [dataArray{1:end-1}];
%% Clear temporary variables
clearvars filename delimiter startRow endRow formatSpec fileID block dataArrayBlock dataArray ans;


for i = 1: 2: 47
errorpercentage_test[i] = results(i);
end

for i = 2: 2: 48
errorpercentage_validation[i] = results(i);
end



